package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.model.CollectionBlurb
import com.soblemprolved.interfaceofourown.service.converters.JsoupHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

object CollectionsSearchConverter : Converter<ResponseBody, CollectionsSearchConverter.Result> {
    data class Result(
        val collectionCount: Int,
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
                    it[1].toInt()
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
