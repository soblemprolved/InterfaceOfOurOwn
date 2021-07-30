package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.model.CollectionBlurb
import okhttp3.Response
import org.jsoup.Jsoup

object CollectionsSearchConverter: Converter<CollectionsSearchConverter.Result> {
    data class Result(
        val collectionCount: Int,
        val collections: List<CollectionBlurb>
    )

    override fun convert(response: Response): Result {
        when (response.code) {
            200 -> return parse(response.body!!.string())
            else -> TODO()
        }
    }

    fun parse(html: String): Result {
        val doc = Jsoup.parse(html)
        val collectionCount = doc.selectFirst("div#main > h3.heading")
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
        val collectionBlurbs = blurbElements.map { Converter.parseCollectionBlurbElement(it) }

        return Result(collectionCount, collectionBlurbs)
    }
}
