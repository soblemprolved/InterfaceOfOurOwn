package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.AO3Client
import com.soblemprolved.orpheus.model.*
import com.soblemprolved.orpheus.extensions.AO3ClientParameterResolver
import com.soblemprolved.orpheus.service.models.AO3Response
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Month

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkRequestTest(private val client: AO3Client) {
    @Test
    fun `Successfully request non-adult oneshot work`() {
        val workRequest = WorkRequest.withDefaultConverter(2094438)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request adult oneshot work`() {
        val workRequest = WorkRequest.withDefaultConverter(2080878)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request work with anonymous author`() {
        val workRequest = WorkRequest.withDefaultConverter(32734699)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
        require(response is AO3Response.Success)
        with (response.value.work) {
            require(this is MultiChapterOrIncompleteWork)
            require(this.authors.size == 1)
//            require(this.giftees.size == 1)   // this shit dont work yet!
        }
    }

    @Test
    fun `Successfully request non-adult multi-chapter completed work`() {
        val workRequest = WorkRequest.withDefaultConverter(8337607)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult multi-chapter incomplete work`() {
        val workRequest = WorkRequest.withDefaultConverter(537876)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult single-chapter incomplete work`() {
        val workRequest = WorkRequest.withDefaultConverter(602)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Work metadata should be correct for single-chapter work id #2080878 (I am Groot by sherlocksmyth)`() {
        val workRequest = WorkRequest.withDefaultConverter(2080878)
        val response = runBlocking { client.execute(workRequest) }
        when (response) {
            is AO3Response.Success -> {
                val work = response.value.work
                with (work) {
                    assertTrue(this is SingleChapterWork)
                    require(this is SingleChapterWork)
                    assertTrue(
                        publishedDate.year == 2014
                                && publishedDate.month == Month.AUGUST
                                && publishedDate.dayOfMonth == 4
                                && lastUpdatedDate == publishedDate   // might fail if not a copy
                                && wordCount == 1308
                                && chapterCount == 1
                                && maxChapterCount == 1
                                && rating == com.soblemprolved.orpheus.model.Rating.EXPLICIT
                                && warnings.size == 1
                                && warnings[0] == com.soblemprolved.orpheus.model.Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS
                                && language == com.soblemprolved.orpheus.model.Language.EN
                    )
                }
            }
            else -> fail()
        }
    }

    @Test
    fun `Work metadata should be correct for multi-chapter work id #257248 (Two Of Us by PandoraCulpa)`() {
        val workRequest = WorkRequest.withDefaultConverter(257248)
        val response = runBlocking { client.execute(workRequest) }
        when (response) {
            is AO3Response.Success -> {
                val work = response.value.work
                with (work) {
                    assertTrue(this is MultiChapterOrIncompleteWork)
                    require(this is MultiChapterOrIncompleteWork)
                    assertTrue(
                        publishedDate.year == 2010
                                && publishedDate.month == Month.APRIL
                                && publishedDate.dayOfMonth == 10
                                && lastUpdatedDate.year == 2010   // might fail if not a copy
                                && lastUpdatedDate.month == Month.APRIL
                                && lastUpdatedDate.dayOfMonth == 10
                                && wordCount == 53681
                                && chapterCount == 8
                                && maxChapterCount == 8
                                && rating == com.soblemprolved.orpheus.model.Rating.TEEN
                                && warnings.size == 1
                                && warnings[0] == com.soblemprolved.orpheus.model.Warning.NO_WARNINGS
                                && categories.size == 2
                                && relationships.size == 3
                                && characters.size == 2
                                && freeforms.size == 5
                                && language == com.soblemprolved.orpheus.model.Language.EN
                    )
                }
            }
            else -> fail()
        }
    }

    @Test
    fun `Should return a ServerError`() {

    }

    @Test
    fun `Should throw an unexpected error`() {

    }
}