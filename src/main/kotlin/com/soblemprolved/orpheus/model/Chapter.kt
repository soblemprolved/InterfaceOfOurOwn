package com.soblemprolved.orpheus.model

/**
 * Represents a chapter of a work.
 */
data class Chapter(
    val id: Long,
    val title: String,
    val summary: Html,
    val preChapterNotes: Html,
    val body: Html,
    val postChapterNotes: Html,
)
