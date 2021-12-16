package com.soblemprolved.interfaceofourown.service.converters

import com.soblemprolved.interfaceofourown.model.MultiChapterOrIncompleteWork
import com.soblemprolved.interfaceofourown.model.SingleChapterWork
import com.soblemprolved.interfaceofourown.service.converters.responsebody.WorkConverter
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.time.Month

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkConverterTest {
    @Test
    fun `Work metadata should be correct for single-chapter work id #2080878 (I am Groot by sherlocksmyth)`() {
        val html = File("src/test/resources/responses/works/single-chapter-work_i-am-groot.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with (result.work) {
            require(this is SingleChapterWork)
            assertTrue(
                publishedDate.year == 2014
                        && publishedDate.month == Month.AUGUST
                        && publishedDate.dayOfMonth == 4
                        && lastUpdatedDate == publishedDate   // might fail if not a copy
                        && wordCount == 1308
                        && chapterCount == 1
                        && maxChapterCount == 1
                        && rating == com.soblemprolved.interfaceofourown.model.Rating.EXPLICIT
                        && warnings.size == 1
                        && warnings[0] == com.soblemprolved.interfaceofourown.model.Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS
                        && language == com.soblemprolved.interfaceofourown.model.Language.EN
                // TODO: add other fields
            )
        }
    }

    @Test
    fun `Work metadata should be correct for multi-chapter work id #257248 (Two Of Us by PandoraCulpa)`() {
        val html = File("src/test/resources/responses/works/multi-chapter-work_two-of-us.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with (result.work) {
            require(this is MultiChapterOrIncompleteWork)
            assertTrue(
                publishedDate.year == 2010
                        && publishedDate.month == java.time.Month.APRIL
                        && publishedDate.dayOfMonth == 10
                        && lastUpdatedDate.year == 2010   // might fail if not a copy
                        && lastUpdatedDate.month == java.time.Month.APRIL
                        && lastUpdatedDate.dayOfMonth == 10
                        && wordCount == 53681
                        && chapterCount == 8
                        && maxChapterCount == 8
                        && rating == com.soblemprolved.interfaceofourown.model.Rating.TEEN
                        && warnings.size == 1
                        && warnings[0] == com.soblemprolved.interfaceofourown.model.Warning.NO_WARNINGS
                        && categories.size == 2
                        && relationships.size == 3
                        && characters.size == 2
                        && freeforms.size == 5
                        && language == com.soblemprolved.interfaceofourown.model.Language.EN
                // TODO: add other fields as assertEquals
            )
        }
    }

    @Test
    fun `Should parse works with anonymous authors correctly`() {
        val html = File("src/test/resources/responses/works/anonymous-author.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with(result.work) {
            assertEquals(1, 1)
        }
    }

    @Test
    fun `Should parse works with multiple authors correctly`() {
        val html = File("src/test/resources/responses/works/multiple-authors_pass-the-compass.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with(result.work) {
            assertEquals(3, authors.size)
        }
    }

    @Test
    fun `Should parse works with multiple giftees correctly`() {
        val html = File("src/test/resources/responses/works/multiple-giftees_come-as-you-are.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with(result.work) {
            assertEquals(2, giftees.size)
            with(giftees.map { it.displayName }) {
                assertTrue(this.contains("MsKingBean89"))
                assertTrue(this.contains("iLikeFrogs17"))
            }
        }
    }

    @Test
    fun `Should parse works with single giftee correctly`() {
        val html = File("src/test/resources/responses/works/single-giftee_family-ties.in")
            .readText()
        val responseBody = html.toResponseBody()
        val result = WorkConverter.convert(responseBody)
        with(result.work) {
            assertEquals(1, giftees.size)
            assertEquals("MsKingBean89", giftees[0].displayName)
        }
    }
}
