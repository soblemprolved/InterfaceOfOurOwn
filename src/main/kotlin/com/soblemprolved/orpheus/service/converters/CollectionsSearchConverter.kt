package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.model.Collection
import okhttp3.Response
import org.jsoup.Jsoup

object CollectionsSearchConverter: Converter<List<Collection>> {
    data class Result(
        val collectionCount: Int,
        val collections: List<Collection>
    )

    override fun convert(response: Response): List<Collection> {

        TODO("Not yet implemented")
    }

    fun parse(html: String) {
        val doc = Jsoup.parse(html)

    }

}
