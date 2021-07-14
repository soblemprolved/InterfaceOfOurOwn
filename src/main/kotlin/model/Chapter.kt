package model

data class Chapter(
    val id: Long,
    val title: String,
    val summary: Html,
    val preChapterNotes: Html,
    val body: Html,
    val postChapterNotes: Html,
)
