package com.soblemprolved.interfaceofourown.service.converters.responsebody

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GetCsrfConverterTest {
    @Test
    fun `Should return the CSRF Token`() {
        val html = File("src/test/resources/responses/bookmarks-by-tag/bookmarks-with-ext-works.in")
            .readText()
        val responseBody = html.toResponseBody()
        val csrf = GetCsrfConverter.convert(responseBody)
        val actualCsrf = "YSRk7BlkWlxe0WvOdOGakq8dzY4yWhXXgS4q_rsOi4Ix---gEYV7Vowlqt7bkMYjTLM5qJEfluACzKpD2sNHVw"
        assertEquals(actualCsrf, csrf.value)
    }
}
