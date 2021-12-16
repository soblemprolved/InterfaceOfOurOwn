package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.model.WorkBlurb
import com.soblemprolved.interfaceofourown.service.converters.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

object WorksByTagConverter : Converter<ResponseBody, WorksByTagConverter.Result> {
    data class Result(
        val tag: String,
        val workCount: Int,
        val workBlurbs: List<WorkBlurb>,
    )

    override fun convert(value: ResponseBody): Result {
        val html = value.string()
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
            JsoupHelper.parseWorkBlurbElement(workIndex)
        }

        return Result(tagName, workCount, workBlurbs)
    }
}
