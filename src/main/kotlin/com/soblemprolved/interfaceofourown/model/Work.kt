package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

sealed interface Work {
    val id: Long
    val title: String
    val authors: List<UserName>
    val giftees: List<UserName>
    val publishedDate: LocalDate
    val lastUpdatedDate: LocalDate   // only found in work
    val rating: Rating
    val warnings: List<Warning>
    val categories: List<Category>
    val fandoms: List<String>
    val relationships: List<String>
    val characters: List<String>
    val freeforms: List<String>
    val summary: Html
    val language: Language
    val wordCount: Int
    val chapterCount: Int
    val maxChapterCount: Int
    val commentCount: Int
    val kudosCount: Int   // kudos is singular, from the Greek.
    val bookmarkCount: Int
    val hitCount: Int
    val preWorkNotes: Html
    val postWorkNotes: Html
    val workskin: Css
    val isComplete: Boolean
}

data class SingleChapterWork(
    override val id: Long,
    override val title: String,
    override val authors: List<UserName>,
    override val giftees: List<UserName>,
    override val publishedDate: LocalDate,
    override val lastUpdatedDate: LocalDate, // only found in work
    override val rating: Rating,
    override val warnings: List<Warning>,
    override val categories: List<Category>,
    override val fandoms: List<String>,  // is there even a point in wrapping this as a tag? maintag is understandable but not this.
    override val relationships: List<String>,
    override val characters: List<String>,
    override val freeforms: List<String>,
    override val summary: Html,
    override val language: Language,
    override val wordCount: Int,
    override val chapterCount: Int,
    override val maxChapterCount: Int,
    override val commentCount: Int,
    override val kudosCount: Int,    // kudos is singular, from the Greek.
    override val bookmarkCount: Int,
    override val hitCount: Int,
    override val preWorkNotes: Html,
    override val postWorkNotes: Html,
    override val workskin: Css,
    // single-chapter specific properties
    val body: Html   // oneshots only need a body
) : Work {
    override val isComplete: Boolean = chapterCount == maxChapterCount
}

data class MultiChapterOrIncompleteWork(
    override val id: Long,
    override val title: String,
    override val authors: List<UserName>,
    override val giftees: List<UserName>,
    override val publishedDate: LocalDate,
    override val lastUpdatedDate: LocalDate, // only found in work
    override val rating: Rating,
    override val warnings: List<Warning>,
    override val categories: List<Category>,
    override val fandoms: List<String>,
    override val relationships: List<String>,
    override val characters: List<String>,
    override val freeforms: List<String>,
    override val summary: Html,
    override val language: Language,
    override val wordCount: Int,
    override val chapterCount: Int,
    override val maxChapterCount: Int,
    override val commentCount: Int,
    override val kudosCount: Int,    // kudos is singular, from the Greek.
    override val bookmarkCount: Int,
    override val hitCount: Int,
    override val preWorkNotes: Html,
    override val postWorkNotes: Html,
    override val workskin: Css,
    // multi-chapter specific properties
    val chapters: List<Chapter>,
    // TODO: add series name(multiple) and position in series? is position necessary?
): Work {
    init {
        require(id >= 0) { "ID cannot be negative!" }
        require(authors.isNotEmpty()) { "There must be at least one author!" }
        require(wordCount >= 0) { "Word count cannot be negative!" }
        require(chapterCount >= 1) { "Chapter count must be at least 1!" }
        require(maxChapterCount >= 0) { "Max chapters cannot be negative!" }
        require(commentCount >= 0) { "Comment count cannot be negative!" }
        require(kudosCount >= 0) { "Kudos count cannot be negative!" }
        require(bookmarkCount >= 0) { "Bookmark count cannot be negative!" }
        require(hitCount >= 0) { "Hit count cannot be negative!" }
    }

    override val isComplete: Boolean = chapterCount == maxChapterCount   // AFAIK this is the logic implemented in the Archive
}
