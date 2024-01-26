package com.soblemprolved.interfaceofourown.converters.responsebody

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagBookmarksConverterTest {
    @Test
    fun `should parse page with external works`() {
        val html = File("src/test/resources/responses/bookmarks-by-tag/bookmarks-with-ext-works.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagBookmarksConverter.convert(responseBody)
        assertTrue(result.bookmarksCount == 3416 && result.bookmarksBlurbs.size == 20)
    }

    @Test
    fun `should parse page with series`() {
        val html = File("src/test/resources/responses/bookmarks-by-tag/bookmarks-with-series.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagBookmarksConverter.convert(responseBody)
        assertTrue(result.bookmarksCount == 462654 && result.bookmarksBlurbs.size == 20)
    }
}
