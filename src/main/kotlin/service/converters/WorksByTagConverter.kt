package service.converters

import model.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Response
import org.jsoup.Jsoup
import service.models.AO3Error
import java.lang.IllegalStateException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object WorksByTagConverter : Converter<WorksByTagConverter.Result> {
    data class Result(
        val tag: String,
        val workCount: Int,
        val workBlurbs: List<WorkBlurb>,
    )

    override fun convert(response: Response): Result {
        when (response.code) {
            in 500..599 -> throw AO3Error.ServerSideError
            in 400..499 -> throw AO3Error.NotFoundError   // not complete
            302, 304 -> with (response.headers["location"] ?: throw AO3Error.NotFoundError) {
                val segments = this.toHttpUrl().pathSegments
                if (segments.last() == "works") {
                    val mainTag = segments[segments.lastIndex - 1]
                    throw AO3Error.TagSynonymError(mainTag, this)
                } else {
                    throw AO3Error.TagNotFilterableError(this)
                }
            }
            in 200..299 -> return parse(response.body!!.string())
            else -> throw IllegalStateException("Not handled yet")
        }
    }

    fun parse(html: String): Result {
        val doc = Jsoup.parse(html)

        val heading = doc.selectFirst("div#main > h2.heading")
        val tagName = heading.selectFirst("h2.heading > a")
            .ownText()
        val workCount = heading.ownText()
            .split(" of ")
            .last()     // this should give the half of the string containing the number
            .split(" ")
            .first()    // this should give a number
            .let {
                if (it.isBlank()) 0 else it.toInt()
            }

        val workIndexList = doc.select("ol.work.index.group > li.work.blurb.group")
        val workBlurbs: List<WorkBlurb> = workIndexList.map { workIndex ->
            // declarations of subtrees beforehand to make assignment clearer
            val header = workIndex.select("h4.heading").select("a[href]")
            val fandomElements = workIndex.select("h5.fandoms.heading").select("a.tag")
            val requiredTags = workIndex.select("ul.required-tags").select("span.text")
            val userTags = workIndex.select("ul.tags.commas")
            val stats = workIndex.select("dl.stats")
            val chapterInfo = stats.select("dd.chapters").text().split("/")

            // actual work data
            val id = header.first().attr("href").removePrefix("/works/").toLong()
            val title = header.first().text()
            val authors = header.select("a[rel=author]").let {
                if (it.isEmpty()) {
                    doc.select("div#workskin > div.preface.group > h3.byline.heading")
                        .map{ element -> User.from(element.text(), hasUrl = false) }
                } else {
                    it.map { element -> User.from(element.text(), hasUrl = true) }
                }
            }
            val giftees = header.next().select(":not(a[rel=author])")
                .map { User.from(it.text(), hasUrl = true) }  // pseuds can be parsed based on names alone
            val datetime = workIndex.select("p.datetime")
                .text()
                .let {
                    LocalDate.parse(
                        it,
                        DateTimeFormatter.ofPattern("dd MMM yyyy")
                    )
                }
            val fandoms = fandomElements.map { it.text() }
            val rating = requiredTags[0].text().let { Rating.fromName(it) }
            val categories = requiredTags[2].text().split(", ")
                .map { Category.fromName(it) }
            val warnings = userTags.select("li.warnings").map { Warning.fromName(it.text()) }
            val relationships = userTags.select("li.relationships").map { it.text() }
            val characters = userTags.select("li.characters").map { it.text() }
            val freeforms = userTags.select("li.freeforms").map { it.text() }
            val summary = workIndex.select("blockquote.userstuff.summary").text().let { Html(it) }
            val language = stats.select("dd.language").text().let { Language.fromName(it) }
            val words = stats.select("dd.words")
                .text()
                .replace(",","")
                .let { if (it.isBlank()) 0 else it.toInt() }
            val currentChapterCount = chapterInfo[0].replace(",","").toInt()
            val maxChapterCount = chapterInfo[1].toIntOrNull() ?: 0
            val comments = stats.select("dd.comments").text().toIntOrNull() ?: 0
            val kudos = stats.select("dd.kudos").text().toIntOrNull() ?: 0
            val bookmarks = stats.select("dd.bookmarks").text().toIntOrNull() ?: 0
            val hits = stats.select("dd.hits").text().toIntOrNull() ?: 0



            return@map WorkBlurb(
                workId = id,
                title = title,
                authors = authors,
                giftees = giftees,
                lastUpdatedDate = datetime,
                fandoms = fandoms,
                rating = rating,
                warnings = warnings,
                categories = categories,
                characters = characters,
                relationships = relationships,
                freeforms = freeforms,
                summary = summary,
                language = language,
                wordCount = words,
                chapterCount = currentChapterCount,
                maxChapterCount = maxChapterCount,
                commentCount = comments,
                kudosCount = kudos,
                bookmarkCount = bookmarks,
                hitCount = hits,
            )
        }

        return Result(tagName, workCount, workBlurbs)
    }
}
