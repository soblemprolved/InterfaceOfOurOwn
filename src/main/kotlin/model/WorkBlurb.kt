package model

import java.time.LocalDate

data class WorkBlurb(
    val workId: Long,
    val title: String,
    val authors: List<String>,
    val giftees: List<String>,
    val lastUpdatedDate: LocalDate,
    val fandoms: List<String>,
    val rating: Rating,
    val warnings: List<Warning>,
    val categories: List<Category>,
    val characters: List<String>,
    val relationships: List<String>,
    val freeforms: List<String>,
    val summary: String,
    val language: String,
    val wordCount: Int,
    val chapterCount: Int,
    val maxChapterCount: Int,
    val commentCount: Int,
    val kudosCount: Int,    // kudos is singular, from the Greek.
    val bookmarkCount: Int,
    val hitCount: Int
) {
    val isComplete: Boolean = chapterCount == maxChapterCount
}
