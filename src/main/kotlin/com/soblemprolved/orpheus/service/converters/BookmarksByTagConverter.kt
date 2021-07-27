package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.model.*
import okhttp3.Response
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object BookmarksByTagConverter : Converter<BookmarksByTagConverter.Result> {
    data class Result(
        val tag: String,
        val bookmarkedItemCount: Int,
        val bookmarkedItems: List<BookmarksBlurb>
    )

    override fun convert(response: Response): Result {
        when (response.code) {
            in 200..299 -> return parse(response.body!!.string())
        }
        TODO("Not yet implemented")
    }

    fun parse(html: String): Result {
        val doc = Jsoup.parse(html)
        val heading = doc.selectFirst("div#main > h2.heading")
        val tagName = heading.selectFirst("h2.heading > a")
            .ownText()
        val totalBookmarkedItemCount = heading.ownText()
            .split(" of ")
            .last()     // this should give the half of the string containing the number
            .split(" ")
            .first()    // this should give a number
            .let {
                if (it.isBlank()) 0 else it.toInt()
            }

        val bookmarkTrees = doc.select("div#main > ol.bookmark > li.bookmark.blurb")
        val bookmarkBlurbs = bookmarkTrees.map { workIndex ->
            // declarations of subtrees beforehand to make assignment clearer
            val header = workIndex.select("h4.heading").select("a[href]")
            val fandomElements = workIndex.select("h5.fandoms.heading").select("a.tag")
            val requiredTags = workIndex.select("ul.required-tags").select("span.text")
            val userTags = workIndex.select("ul.tags.commas")
            val stats = workIndex.select("dl.stats")
            val bookmarkIndices = workIndex
                .selectFirst("div.recent.module.group")
                .selectFirst("ul.bookmark.index.group")
                .select("li.user.short.blurb.group")
            val bookmarkSummaries = bookmarkIndices.map { index ->
                val user = index.select("div.header > h5.byline > a[href]")
                    .text()
                    .let { displayName -> User.from(displayName, true) }
                val bookmarkDate = index.select("div.header > p.datetime")
                    .text()
                    .let { LocalDate.parse(it, DateTimeFormatter.ofPattern("dd MMM yyyy")) }
                val type = index.selectFirst("div.header > p.status > a > span")
                    .className()
                    .let { className ->
                        when (className) {
                            "public" -> BookmarkType.PUBLIC
                            "rec" -> BookmarkType.RECOMMENDATION
                            else -> throw IllegalArgumentException()   // fail fast
                        }
                    }
                val bookmarkNotes = index.selectFirst("blockquote.userstuff.summary")
                    ?.html()
                    ?.let { Html(it) }
                    ?: Html("")

                val collections = index.select("ul.meta.commas:not(.tags) > li > a[href]")   // exclude tags
                    .map{
                        val url = it.attr("href")
                        val id = url.removePrefix("/collections/")
                        val name = it.text()
                        CollectionName(id = id, name = name)
                    }

                val tags = index.select("ul.meta.tags.commas > li > a")
                    .map { it.text() }

                Bookmark(
                    user = user,
                    tags = tags,
                    collections = collections,
                    date = bookmarkDate,
                    notes = bookmarkNotes,
                    bookmarkType = type
                )
            }

            val chapterInfo = stats.select("dd.chapters").text().split("/")

            // Common data to all types of blurbs
            val title = header.first().text()
            val authors = header.select("a[rel=author]").let {
                if (it.isEmpty()) {
                    doc.select("div#workskin > div.preface.group > h3.byline.heading")
                        .map{ element -> User.from(element.text(), hasUrl = false) }
                } else {
                    it.map { element -> User.from(element.text(), hasUrl = true) }
                }
            }
            val datetime = workIndex.select("p.datetime")
                .text()
                .let {
                    LocalDate.parse(
                        it,
                        DateTimeFormatter.ofPattern("dd MMM yyyy")
                    )
                }
            val fandoms = fandomElements.map { it.text() }

            val categories = requiredTags[2].text().split(", ")
                .map { Category.fromName(it) }

            // execute code based on type of item
            val titleLink = header.first().attr("href")
            with(titleLink) {
                when {
                    startsWith("/works/") -> {
                        val id = titleLink.removePrefix("/works/").toLong()
                        val rating = requiredTags[0].text().let { Rating.fromName(it) }
                        val warnings = userTags.select("li.warnings").map { Warning.fromName(it.text()) }
                        val relationships = userTags.select("li.relationships").map { it.text() }
                        val characters = userTags.select("li.characters").map { it.text() }
                        val freeforms = userTags.select("li.freeforms").map { it.text() }
                        val giftees = header.next().select(":not(a[rel=author])")
                            .map { User.from(it.text(), hasUrl = true) }  // pseuds can be parsed based on names alone
                        val summary = workIndex.selectFirst("blockquote.userstuff.summary")
                            .text()
                            .let { Html(it) }
                        val language = stats.select("dd.language").text().let { Language.fromName(it) }
                        val currentChapterCount = chapterInfo[0].replace(",","").toInt()
                        val maxChapterCount = chapterInfo[1].toIntOrNull() ?: 0
                        val words = stats.select("dd.words")
                            .text()
                            .replace(",","")
                            .let { if (it.isBlank()) 0 else it.toInt() }
                        val bookmarks = stats.select("dd.bookmarks").text().toIntOrNull() ?: 0
                        val comments = stats.select("dd.comments").text().toIntOrNull() ?: 0
                        val kudos = stats.select("dd.kudos").text().toIntOrNull() ?: 0
                        val hits = stats.select("dd.hits").text().toIntOrNull() ?: 0

                        val blurb = WorkBlurb(
                            id = id,
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

                        return@map WorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/series/") -> {
                        val id = titleLink.removePrefix("/series/").toLong()
                        val ratings = requiredTags[0].text()
                            .split(", ")
                            .map { Rating.fromName(it) }
                        val warnings = userTags.select("li.warnings").map { Warning.fromName(it.text()) }
                        val relationships = userTags.select("li.relationships").map { it.text() }
                        val characters = userTags.select("li.characters").map { it.text() }
                        val freeforms = userTags.select("li.freeforms").map { it.text() }
                        val summary = workIndex.selectFirst("blockquote.userstuff.summary")
                            ?.text()
                            ?.let { Html(it) }
                            ?: Html("")

                        val statsNumbers = stats.select("dt").map {
                            it.text()
                                .replace(",","")
                                .toInt()
                        }
                        // order is always words - workcount - bookmarks (optional)
                        val words = statsNumbers[0]
                        val workCount = statsNumbers[1]
                        val bookmarks = statsNumbers.getOrElse(2) { 0 }

                        val blurb = SeriesBlurb(
                            id = id,
                            title = title,
                            authors = authors,
                            lastUpdatedDate = datetime,
                            fandoms = fandoms,
                            ratings = ratings,
                            warnings = warnings,
                            categories = categories,
                            characters = characters,
                            relationships = relationships,
                            freeforms = freeforms,
                            summary = summary,
                            wordCount = words,
                            workCount = workCount,
                            bookmarkCount = bookmarks
                        )

                        return@map SeriesBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/external_works/") -> {
                        val id = titleLink.removePrefix("/external_works/").toLong()
                        val rating = requiredTags[0].text().let { Rating.fromName(it) }
                        val relationships = userTags.select("li.relationships").map { it.text() }
                        val characters = userTags.select("li.characters").map { it.text() }
                        val freeforms = userTags.select("li.freeforms").map { it.text() }
                        val summary = workIndex.selectFirst("blockquote.userstuff.summary")
                            .text()
                            .let { Html(it) }
                        val bookmarks = stats
                            .select("dd > a[href]")
                            .first { it.attr("href").endsWith("/bookmarks") }
                            .text()
                            .replace(",", "")
                            .toInt()

                        val blurb = ExternalWorkBlurb(
                            id = id,
                            title = title,
                            authors = authors,
                            lastUpdatedDate = datetime,
                            fandoms = fandoms,
                            rating = rating,
                            categories = categories,
                            characters = characters,
                            relationships = relationships,
                            freeforms = freeforms,
                            summary = summary,
                            bookmarkCount = bookmarks
                        )

                        return@map ExternalWorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }

        return Result(tag = tagName, bookmarkedItemCount = totalBookmarkedItemCount, bookmarkedItems = bookmarkBlurbs)
    }
}
