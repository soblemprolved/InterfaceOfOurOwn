package service.requests

import AO3Client
import extensions.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import service.models.AO3Response
import java.time.Month

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkRequestTest(private val client: AO3Client) {
    @Test
    fun `Successfully request non-adult oneshot work`() {
        val workRequest = WorkRequest.from(2094438)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request adult oneshot work`() {
        val workRequest = WorkRequest.from(2080878)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request non-adult multi-chapter completed work`() {
        val workRequest = WorkRequest.from(8337607)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult multi-chapter incomplete work`() {
        val workRequest = WorkRequest.from(537876)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult single-chapter incomplete work`() {
        val workRequest = WorkRequest.from(602)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Work metadata should be correct for single-chapter work id #2080878 (I am Groot by sherlocksmyth)`() {
        val workRequest = WorkRequest.from(2080878)
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
                                && rating == Rating.EXPLICIT
                                && warnings.size == 1
                                && warnings[0] == Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS
                                && language == Language.EN
                    )
                }
            }
            else -> fail()
        }
    }

    @Test
    fun `Work metadata should be correct for multi-chapter work id #257248 (Two Of Us by PandoraCulpa)`() {
        val workRequest = WorkRequest.from(257248)
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
                                && rating == Rating.TEEN
                                && warnings.size == 1
                                && warnings[0] == Warning.NO_WARNINGS
                                && categories.size == 2
                                && relationships.size == 3
                                && characters.size == 2
                                && freeforms.size == 5
                                && language == Language.EN
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