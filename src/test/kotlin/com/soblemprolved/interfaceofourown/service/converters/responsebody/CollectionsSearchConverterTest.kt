package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.model.ChallengeType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CollectionsSearchConverterTest {
    @Test
    fun `Should parse a normal page without issue`() {
        /*
        This sample also contains the following types:
            Both closed and open collections
            Moderated and unmoderated collections
            Anonymous and non-anonymous collections
            Revealed and unrevealed collections.
            Prompt meme challenge and no challenges.
        The only thing missing is gift exchange challenge.
         */
        val html = File("src/test/resources/responses/collections/normal-page.in")
            .readText()
        val responseBody = html.toResponseBody()
        val response = CollectionsSearchConverter.convert(responseBody)
        assertEquals(20, response.collections.size)
    }

    @Test
    fun `Should parse gift exchange challenges without issue`() {
        /* All blurbs here represent gift exchange challenge collections */
        val html = File("src/test/resources/responses/collections/page-with-gift-exchange-challenge.in")
            .readText()
        val responseBody = html.toResponseBody()
        val response = CollectionsSearchConverter.convert(responseBody)
        assertEquals(20, response.collections.size)
        response.collections.forEach { assertEquals(ChallengeType.GIFT_EXCHANGE, it.challenge) }
    }

    @Test
    fun `Should parse an empty page without issue`() {
        val html = File("src/test/resources/responses/collections/empty-page.in")
            .readText()
        val responseBody = html.toResponseBody()
        val response = CollectionsSearchConverter.convert(responseBody)
        assertEquals(0, response.collections.size)
    }
}
