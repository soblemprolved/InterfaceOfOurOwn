package model

/**
 * Represents a chapter of a work.
 */
data class Chapter(
    val id: Long,   // should I make this nullable to reduce network calls?
    val title: String,
    val summary: Html,
    val preChapterNotes: Html,
    val body: Html,
    val postChapterNotes: Html,
)
