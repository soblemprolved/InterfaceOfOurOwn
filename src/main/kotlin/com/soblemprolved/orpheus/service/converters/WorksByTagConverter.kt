package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.model.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Response
import org.jsoup.Jsoup
import com.soblemprolved.orpheus.service.models.AO3Error
import java.lang.IllegalStateException

object WorksByTagConverter : Converter<WorksByTagConverter.Result> {
    data class Result(
        val tag: String,
        val workCount: Int,
        val workBlurbs: List<WorkBlurb>,
    )

    override fun convert(response: Response): Result {
        when (response.code) {
            in 500..599 -> throw AO3Error.ServerSideError
            in 400..499 -> throw AO3Error.NotFoundError   // not complete
            302, 304 -> with (response.headers["location"] ?: throw AO3Error.NotFoundError) {
                val segments = this.toHttpUrl().pathSegments
                if (segments.last() == "works") {
                    val mainTag = segments[segments.lastIndex - 1]
                    throw AO3Error.TagSynonymError(mainTag, this)
                } else {
                    throw AO3Error.TagNotFilterableError(this)
                }
            }
            in 200..299 -> return parse(response.body!!.string())
            else -> throw IllegalStateException("Not handled yet")
        }
    }

    fun parse(html: String): Result {
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
            Converter.parseWorkBlurbElement(workIndex)
        }

        return Result(tagName, workCount, workBlurbs)
    }
}
