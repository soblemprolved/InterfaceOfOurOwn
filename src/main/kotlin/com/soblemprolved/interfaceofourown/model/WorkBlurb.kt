package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class WorkBlurb(
    val id: Long,
    val title: String,
    val authors: List<UserName>,   // no authors means its anonymous
    val giftees: List<UserName>,
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
    val hitCount: Int
) {
    init {
        require(id >= 0) { "ID cannot be negative!" }
        require(wordCount >= 0) { "Word count cannot be negative!" }
        require(chapterCount >= 1) { "Chapter count must be at least 1!" }
        require(maxChapterCount >= 0) { "Max chapters cannot be negative!" }
        require(commentCount >= 0) { "Comment count cannot be negative!" }
        require(kudosCount >= 0) { "Kudos count cannot be negative!" }
        require(bookmarkCount >= 0) { "Bookmark count cannot be negative!" }
        require(hitCount >= 0) { "Hit count cannot be negative!" }
    }

    val isComplete: Boolean = chapterCount == maxChapterCount
}
