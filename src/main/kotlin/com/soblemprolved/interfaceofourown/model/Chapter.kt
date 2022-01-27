package com.soblemprolved.interfaceofourown.model

/**
 * Represents a chapter of a work.
 */
data class Chapter(
    /**
     * The unique ID of the chapter.
     */
    val id: Long,

    /**
     * The title of the chapter.
     */
    val title: String,

    /**
     * The raw HTML of the summary.
     */
    val summary: Html,

    /**
     * The raw HTML of the pre-chapter notes.
     */
    val preChapterNotes: Html,

    /**
     * The raw HTML of the chapter body.
     */
    val body: Html,

    /**
     * The raw HTML of the post-chapter notes.
     */
    val postChapterNotes: Html,
)
