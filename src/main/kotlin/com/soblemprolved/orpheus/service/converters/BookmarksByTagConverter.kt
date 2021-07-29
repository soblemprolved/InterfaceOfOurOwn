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
            val bookmarkIndices = workIndex
                .selectFirst("div.recent.module.group")
                .selectFirst("ul.bookmark.index.group")
                .select("li.user.short.blurb.group")
            val bookmarkSummaries = bookmarkIndices.map { index ->
                val user = index.select("div.header > h5.byline > a[href]")
                    .text()
                    .let { displayName -> User.from(displayName, true) }
                val bookmarkDate = index.selectFirst("div.header > p.datetime")
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

            // execute code based on type of item
            val titleLink = workIndex.selectFirst("h4.heading > a[href]")
                .attr("href")
            with(titleLink) {
                when {
                    startsWith("/works/") -> {
                        val blurb = Converter.parseWorkBlurbSnippet(workIndex)
                        return@map WorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/series/") -> {
                        val blurb = Converter.parseSeriesBlurbSnippet(workIndex)
                        return@map SeriesBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/external_works/") -> {
                        val blurb = Converter.parseExternalWorkBlurbSnippet(workIndex)
                        return@map ExternalWorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }

        return Result(tag = tagName, bookmarkedItemCount = totalBookmarkedItemCount, bookmarkedItems = bookmarkBlurbs)
    }
}
