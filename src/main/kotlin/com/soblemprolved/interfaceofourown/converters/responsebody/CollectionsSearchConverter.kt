package com.soblemprolved.interfaceofourown.converters.responsebody

import com.soblemprolved.interfaceofourown.model.CollectionBlurb
import com.soblemprolved.interfaceofourown.converters.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object CollectionsSearchConverter : Converter<ResponseBody, CollectionsSearchConverter.Result> {
    data class Result(

        /**
         * Number of collections matching the filter arguments in the corresponding request.
         */
        val collectionCount: Int,

        /**
         * Summary blurbs of the collections matching the filter arguments. As the results are paginated, this will only
         * retrieve the blurbs corresponding to the page specified in the corresponding request.
         */
        val collections: List<CollectionBlurb>
    )

    override fun convert(value: ResponseBody): Result {
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

        return Result(collectionCount, collectionBlurbs)
    }
}