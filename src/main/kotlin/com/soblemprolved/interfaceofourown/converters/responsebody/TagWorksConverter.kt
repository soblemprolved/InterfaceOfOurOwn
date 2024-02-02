package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.model.WorkBlurb
import com.soblemprolved.interfaceofourown.converters.JsoupHelper
import com.soblemprolved.interfaceofourown.model.pages.TagWorksPage
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

internal object TagWorksConverter : Converter<ResponseBody, TagWorksPage> {
    override fun convert(value: ResponseBody): TagWorksPage {
        val html = value.string()
        val doc = Jsoup.parse(html)

        val heading = doc.selectFirst("div#main > h2.heading")!!
        val tagName = heading.selectFirst("h2.heading > a")!!
            .ownText()
        val workCount = heading.ownText()
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

        val workIndexList = doc.select("ol.work.index.group > li.work.blurb.group")
        val workBlurbs: List<WorkBlurb> = workIndexList.map { workIndex ->
            // declarations of subtrees beforehand to make assignment clearer
            JsoupHelper.parseWorkBlurbElement(workIndex)
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

        return TagWorksPage(
            tag = tagName,
            worksCount = workCount,
            currentPageCount = currentPageCount,
            maxPageCount = maxPageCount,
            workBlurbs = workBlurbs
        )
    }
}
