package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

sealed interface Work {
    /**
     * The unique ID of the entire work.
     */
    val id: Long

    /**
     * The title of the work.
     */
    val title: String

    /**
     * The list of authors of the work.
     */
    val authors: List<UserName>

    /**
     * The list of giftees for the work.
     */
    val giftees: List<UserName>

    /**
     * The date of publication of this work.
     */
    val publishedDate: LocalDate

    /**
     * The date of update of this work.
     */
    val lastUpdatedDate: LocalDate   // only found in work

    /**
     * The content rating of this work (e.g. General, Teens, etc.).
     */
    val rating: Rating

    /**
     * The content warnings for the work (e.g. Graphic Depictions of Violence, etc.).
     */
    val warnings: List<Warning>

    /**
     * The categories of the relationships featured in this work.
     */
    val categories: List<Category>

    /**
     * The fandom tags associated with this work.
     */
    val fandoms: List<String>

    /**
     * The relationship tags associated with this work.
     */
    val relationships: List<String>

    /**
     * The character tags associated with this work.
     */
    val characters: List<String>

    /**
     * The freeform tags associated with the work.
     */
    val freeforms: List<String>

    /**
     * The raw HTML of the summary.
     */
    val summary: Html

    /**
     * The language in which the work was written in.
     */
    val language: Language

    /**
     * The total number of words in the entire work.
     */
    val wordCount: Int

    /**
     * The current number of chapters for the work.
     */
    val chapterCount: Int

    /**
     * The projected number of chapters for the work.
     * If the number is unknown (represented as a "?" on AO3), then [maxChapterCount] is 0.
     */
    val maxChapterCount: Int

    /**
     * The number of comments left on the work.
     */
    val commentCount: Int

    /**
     * The number of kudos that this work has received.
     */
    val kudosCount: Int   // kudos is singular, from the Greek.

    /**
     * The number of bookmarks that this work has received.
     */
    val bookmarkCount: Int

    /**
     * The number of hits that this work has received.
     */
    val hitCount: Int

    /**
     * The raw HTML of the pre-work notes.
     */
    val preWorkNotes: Html

    /**
     * The raw HTML of the post-work notes.
     */
    val postWorkNotes: Html

    /**
     * The raw HTML of the workskin.
     */
    val workskin: Css

    /**
     * Indicates whether the work is complete.
     * A completed work has the same [chapterCount] and [maxChapterCount].
     */
    val isComplete: Boolean
}

// TODO: do I really need to split single chapter and multi chapter works
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
    /**
     * The raw HTML of the body of the work.
     */
    val body: Html   // oneshots only need a body
) : Work {
    override val isComplete: Boolean = chapterCount == maxChapterCount
}

/**
 * Note that [MultiChapterWork]s may be incomplete.
 */
data class MultiChapterWork(
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
