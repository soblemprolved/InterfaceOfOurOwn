package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.model.WorkBlurb
import com.soblemprolved.interfaceofourown.converters.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object WorksByTagConverter : Converter<ResponseBody, WorksByTagConverter.Result> {
    data class Result(

        /**
         * Name of the tag.
         */
        val tag: String,

        /**
         * Number of works associated with the tag.
         */
        val workCount: Int,

        /**
         * Summary blurbs of the works associated with the tag. As the results are paginated, this will only
         * retrieve the blurbs corresponding to the page specified in the corresponding request.
         */
        val workBlurbs: List<WorkBlurb>,
    )

    override fun convert(value: ResponseBody): Result {
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

        return Result(tagName, workCount, workBlurbs)
    }
}
