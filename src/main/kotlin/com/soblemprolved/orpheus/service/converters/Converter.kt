package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.model.*
import okhttp3.Response
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface Converter<T> {
    fun convert(response: Response): T

    companion object {
        fun getCsrfFromJsoupDoc(doc: Document): String {
            return doc.selectFirst("meta[name=csrf-token]")
                .attr("content")
        }

        fun decodeTag(encodedTag: String): String {
            return encodedTag.replace("*s*", "/")
                .replace("*a*", "&")
                .replace("*d*", ".")
                .replace("*q*", "?")
                .replace("*h*", "#")
        }

        // TODO: add the blurb parsing code as discrete functions
        fun parseWorkBlurbSnippet(workIndex: Element): WorkBlurb {
            val heading = workIndex.selectFirst("div.header.module > h4.heading")
            val fandomElements = workIndex.select("h5.fandoms.heading").select("a.tag")
            val requiredTags = workIndex.select("ul.required-tags").select("span.text")
            val userTags = workIndex.select("ul.tags.commas")
            val stats = workIndex.select("dl.stats")
            val chapterInfo = stats.select("dd.chapters").text().split("/")

            // actual work data
            val id = heading.selectFirst("h4.heading > a[href]")
                .attr("href")
                .removePrefix("/works/")
                .toLong()
            val title = heading.selectFirst("h4.heading > a[href]").text()
            val authors = heading.select("a[rel=author]").let {
                if (it.isEmpty()) {
                    heading.ownText()   // "by Anonymous for"
                        .trim()   // remove leading and trailing whitespace first
                        .removePrefix("by")
                        .removeSuffix("for")
                        .trim()
                        .split(", ")
                        .map { name -> User.from(name, hasUrl = false) }
                } else {
                    it.map { element -> User.from(element.text(), hasUrl = true) }
                }
            }

            val giftees = heading.select("h4.heading > a[href$=/gifts]")   // ending with /gifts
                .map { User.from(it.text(), hasUrl = true) }   // pseuds can be parsed based on names alone

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
                .mapNotNull { Category.fromName(it) }
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

            return WorkBlurb(
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
        }

        fun parseBookmarkBlurbSnippet(bookmarkBlurbHtmlSnippet: Element) {

        }

        fun parseSeriesBlurbSnippet(seriesBlurbHtmlSnippet: Element) {

        }
    }
}
