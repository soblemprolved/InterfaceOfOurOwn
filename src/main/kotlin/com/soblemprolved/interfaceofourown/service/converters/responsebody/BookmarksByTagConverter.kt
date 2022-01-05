package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb
import com.soblemprolved.interfaceofourown.model.ExternalWorkBookmarksBlurb
import com.soblemprolved.interfaceofourown.model.SeriesBookmarksBlurb
import com.soblemprolved.interfaceofourown.model.WorkBookmarksBlurb
import com.soblemprolved.interfaceofourown.service.converters.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

object BookmarksByTagConverter : Converter<ResponseBody, BookmarksByTagConverter.Result> {
    data class Result(
        val tag: String,
        val bookmarkedItemCount: Int,
        val bookmarkedItems: List<BookmarksBlurb>
    )

    override fun convert(value: ResponseBody): Result {
        val html = value.string()
        val doc = Jsoup.parse(html)
        val heading = doc.selectFirst("div#main > h2.heading")!!
        val tagName = heading.selectFirst("h2.heading > a")!!
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
            val bookmarkElements = workIndex
                .selectFirst("div.recent.module.group")!!
                .selectFirst("ul.bookmark.index.group")!!
                .select("li.user.short.blurb.group")
            val bookmarkSummaries = bookmarkElements.map { element ->
                JsoupHelper.parseBookmarkElement(element)
            }

            // execute code based on type of item
            val titleLink = workIndex.selectFirst("h4.heading > a[href]")!!
                .attr("href")
            with(titleLink) {
                when {
                    startsWith("/works/") -> {
                        val blurb = JsoupHelper.parseWorkBlurbElement(workIndex)
                        return@map WorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/series/") -> {
                        val blurb = JsoupHelper.parseSeriesBlurbElement(workIndex)
                        return@map SeriesBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    startsWith("/external_works/") -> {
                        val blurb = JsoupHelper.parseExternalWorkBlurbElement(workIndex)
                        return@map ExternalWorkBookmarksBlurb(blurb, bookmarkSummaries)
                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }

        return Result(tag = tagName, bookmarkedItemCount = totalBookmarkedItemCount, bookmarkedItems = bookmarkBlurbs)
    }
}
