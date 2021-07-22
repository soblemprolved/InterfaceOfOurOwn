package model

import java.time.LocalDate

sealed interface Work

data class SingleChapterWork(
    val id: Long,
    val title: String,
    val authors: List<User>,
    val giftees: List<User>,
    val publishedDate: LocalDate,
    val lastUpdatedDate: LocalDate, // only found in work
    val rating: Rating,
    val warnings: List<Warning>,
    val categories: List<Category>,
    val fandoms: List<String>,  // is there even a point in wrapping this as a tag? maintag is understandable but not this.
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
    val hitCount: Int,
    // work-specific properties
    val preWorkNotes: Html,
    val body: Html,     // oneshots only need a body
    val postWorkNotes: Html,
    val workskin: Css
) : Work

data class MultiChapterOrIncompleteWork(
    val id: Long,
    val title: String,
    val authors: List<User>,
    val giftees: List<User>,
    val publishedDate: LocalDate,
    val lastUpdatedDate: LocalDate, // only found in work
    val rating: Rating,
    val warnings: List<Warning>,
    val categories: List<Category>,
    val fandoms: List<String>,  // is there even a point in wrapping this as a tag? maintag is understandable but not this.
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
    val hitCount: Int,
    // work-specific properties
    val preWorkNotes: Html,
    val chapters: List<Chapter>,   // if I make the id nullable, I can handle both oneshots and other works in 1 call
    val postWorkNotes: Html,
    val workskin: Css
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

    val isComplete: Boolean = chapterCount == maxChapterCount   // AFAIK this is the logic implemented in the Archive
}
