package model

import java.time.LocalDate

data class Work(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val giftees: List<String>,
    val publishedDate: LocalDate,
    val lastUpdatedDate: LocalDate,
    val rating: Rating,
    val warnings: List<Warning>,
    val categories: List<Category>,
    val fandoms: List<String>,  // leave tags as strings
    val relationships: List<String>,
    val characters: List<String>,
    val freeforms: List<String>,
    val summary: Html,
    val language: Language,
    val wordCount: Int,
    val chapterCount: Int,
    val maxChapterCount: Int,
    val commentCount: Int,
    val kudosCount: Int,    // kudos is singular, from the Greek.
    val bookmarkCount: Int,
    val hitCount: Int,
    val preWorkNotes: Html,
    val chapters: List<Chapter>,
    val postWorkNotes: Html,
    val workskin: Css
) {
    val isComplete: Boolean = chapterCount == maxChapterCount   // AFAIK this is the logic implemented in the Archive
}
