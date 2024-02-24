package com.soblemprolved.interfaceofourown.features.users.bookmarks

import com.soblemprolved.interfaceofourown.features.common.helpers.JsoupHelper
import com.soblemprolved.interfaceofourown.model.ExternalWorkBookmarksBlurb
import com.soblemprolved.interfaceofourown.model.SeriesBookmarksBlurb
import com.soblemprolved.interfaceofourown.model.WorkBookmarksBlurb
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

internal object UserBookmarksConverter : Converter<ResponseBody, UserBookmarksPage> {
    override fun convert(value: ResponseBody): UserBookmarksPage {
        val html = value.string()
        val doc = Jsoup.parse(html)
        val heading = doc.selectFirst("div#main > h2.heading")!!
        val userName = heading.ownText()
            .split(" ")
            .last()
        val totalBookmarkedItemCount = heading.ownText()
            .split(" of ")
            .last()     // this should give the half of the string containing the number
            .split(" ")
            .first()    // this should give a number
            .let {
                if (it.isBlank()) {
                    0
                } else {
                    val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH) as DecimalFormat
                    numberFormat.parse(it).toInt()
                }
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

        val maxPageCount = doc.selectFirst("ol.pagination.actions")
            ?.select("li > a")
            ?.let { it[it.size - 2] }
            ?.ownText()
            ?.toInt()
            ?: 1

        val currentPageCount = doc.selectFirst("ol.pagination.actions > li > span.current")
            ?.ownText()
            ?.toInt()
            ?: 1

        return UserBookmarksPage(
            user = userName,
            bookmarksCount = totalBookmarkedItemCount,
            currentPageCount = currentPageCount,
            maxPageCount = maxPageCount,
            bookmarksBlurbs = bookmarkBlurbs
        )
    }
}
