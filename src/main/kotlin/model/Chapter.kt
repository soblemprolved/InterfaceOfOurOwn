package model

data class Chapter(
    val id: Long,
    val title: String,
    val summary: String,
    val preChapterNotes: String,
    val postChapterNotes: String,
    val body: String
)
