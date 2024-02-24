package com.soblemprolved.interfaceofourown.features.collections.filter

import com.soblemprolved.interfaceofourown.features.common.helpers.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

internal object CollectionsFilterConverter : Converter<ResponseBody, CollectionsFilterPage> {
    override fun convert(value: ResponseBody): CollectionsFilterPage {
        val html = value.string()
        val doc = Jsoup.parse(html)
        val collectionCount = doc.selectFirst("div#main > h3.heading")!!
            .text()
            .removeSuffix(" Collections")
            .split(" of ")
            .let {
                if (it.size == 2) {
                    val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH) as DecimalFormat
                    numberFormat.parse(it[1]).toInt()
                } else {
                    it[0].toIntOrNull()  // if it can be parsed, then there are less than 20 works
                        ?: 0  // if it cannot be parsed, the string is "Sorry, there were no collections found."
                }
            }
        val blurbElements = doc.select("div#main > ul.collection > li.collection.blurb")
        val collectionBlurbs = blurbElements.map { JsoupHelper.parseCollectionBlurbElement(it) }

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

        return CollectionsFilterPage(
            collectionCount = collectionCount,
            currentPageCount = currentPageCount,
            maxPageCount = maxPageCount,
            collectionBlurbs = collectionBlurbs
        )
    }
}
