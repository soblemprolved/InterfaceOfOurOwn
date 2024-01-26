package com.soblemprolved.interfaceofourown.converters.responsebody

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagWorksConverterTest {
    @Test
    fun `Should parse normal page correctly`() {
        val html = File("src/test/resources/responses/works-by-tag/normal-page.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagWorksConverter.convert(responseBody)
        assertEquals(20, result.workBlurbs.size)
        // TODO: fill in the rest
    }

    @Test
    fun `Should parse tags with less than 20 works correctly`() {
        val html = File("src/test/resources/responses/works-by-tag/less-than-20-works.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagWorksConverter.convert(responseBody)
        assertEquals(1, result.workBlurbs.size)
        assertEquals(1, result.worksCount)
        // TODO: fill in the rest
    }

    @Test
    fun `Should parse non-standard ratings correctly`() {
        val html = File("src/test/resources/responses/works-by-tag/nonstandard-works-with-no-rating.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagWorksConverter.convert(responseBody)
        // TODO: fill in the rest
    }

    @Test
    fun `Should return 0 works when parsing empty page`() {
        val html = File("src/test/resources/responses/works-by-tag/page-num-exceeding-max-pages-normal.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagWorksConverter.convert(responseBody)
        assertEquals(0, result.workBlurbs.size)
    }

    @Test
    fun `Should return 0 works when parsing empty page for tags with less than 20 works`() {
        val html = File("src/test/resources/responses/works-by-tag/page-num-exceeding-max-pages-less-than-20-works.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = TagWorksConverter.convert(responseBody)
        assertEquals(0, result.workBlurbs.size)
    }
}
