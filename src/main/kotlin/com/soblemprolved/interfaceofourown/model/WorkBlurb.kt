package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class WorkBlurb(

    /**
     * The unique ID of the entire work.
     */
    val id: Long,

    /**
     * The title of the work.
     */
    val title: String,

    /**
     * The list of authors of the work.
     */
    val authors: List<UserReference>,   // no authors means its anonymous

    /**
     * The list of giftees for the work.
     */
    val giftees: List<UserReference>,

    /**
     * The date of the most recent update to this work.
     */
    val lastUpdatedDate: LocalDate,

    /**
     * The content rating of this work (e.g. General).
     */
    val rating: Rating,

    /**
     * The content warnings for the work (e.g. Graphic Depictions of Violence).
     */
    val warnings: List<Warning>,

    /**
     * The categories of the relationships featured in this work.
     */
    val categories: List<Category>,

    /**
     * The fandom tags associated with this work.
     */
    val fandoms: List<String>,  // leave tags as strings

    /**
     * The relationship tags associated with this work.
     */
    val relationships: List<String>,

    /**
     * The character tags associated with this work.
     */
    val characters: List<String>,

    /**
     * The freeform tags associated with the work.
     */
    val freeforms: List<String>,

    /**
     * The raw HTML of the summary.
     */
    val summary: Html,

    /**
     * The language in which the work was written in.
     */
    val language: Language,

    /**
     * The total number of words in the entire work.
     */
    val wordCount: Int,

    /**
     * The current number of chapters for the work.
     */
    val chapterCount: Int,

    /**
     * The projected number of chapters for the work.
     * If the number is unknown (represented as a "?" on AO3), then [maxChapterCount] is 0.
     */
    val maxChapterCount: Int,

    /**
     * The number of comments left on the work.
     */
    val commentCount: Int,

    /**
     * The number of kudos that this work has received.
     */
    val kudosCount: Int,    // kudos is singular, from the Greek.

    /**
     * The number of bookmarks that this work has received.
     */
    val bookmarkCount: Int,

    /**
     * The number of hits that this work has received.
     */
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

    /**
     * Indicates whether the work is complete.
     * A completed work has the same [chapterCount] and [maxChapterCount].
     */
    val isComplete: Boolean = chapterCount == maxChapterCount
}
