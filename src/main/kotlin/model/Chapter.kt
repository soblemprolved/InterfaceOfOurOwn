package model

data class Chapter(
    val id: Long,
    val title: String,
    val summary: String,
    val preChapterNotes: String,
    val body: String,
    val postChapterNotes: String,
)
