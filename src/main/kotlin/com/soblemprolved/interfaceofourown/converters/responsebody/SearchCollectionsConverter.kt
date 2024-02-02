package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.model.CollectionBlurb
import com.soblemprolved.interfaceofourown.converters.JsoupHelper
import com.soblemprolved.interfaceofourown.model.pages.SearchCollectionsPage
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

internal object SearchCollectionsConverter : Converter<ResponseBody, SearchCollectionsPage> {
    override fun convert(value: ResponseBody): SearchCollectionsPage {
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

        return SearchCollectionsPage(
            collectionCount = collectionCount,
            currentPageCount = currentPageCount,
            maxPageCount = maxPageCount,
            collectionBlurbs = collectionBlurbs
        )
    }
}
