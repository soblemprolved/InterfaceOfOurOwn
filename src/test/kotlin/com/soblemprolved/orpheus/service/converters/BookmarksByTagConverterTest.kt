package com.soblemprolved.orpheus.service.converters

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BookmarksByTagConverterTest {
    @Test
    fun `should parse page with external works`() {
        val html = File("src/test/resources/responses/bookmarks-by-tag/bookmarks-with-ext-works.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = BookmarksByTagConverter.convert(responseBody)
        assertTrue(result.bookmarkedItemCount == 3416 && result.bookmarkedItems.size == 20)
    }

    @Test
    fun `should parse page with series`() {
        val html = File("src/test/resources/responses/bookmarks-by-tag/bookmarks-with-series.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = BookmarksByTagConverter.convert(responseBody)
        assertTrue(result.bookmarkedItemCount == 462654 && result.bookmarkedItems.size == 20)
    }
}
