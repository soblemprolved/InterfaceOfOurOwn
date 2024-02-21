package com.soblemprolved.interfaceofourown.features.tags.bookmarks

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb

data class TagBookmarksPage(
    val tag: String,
    val currentPageCount: Int,
    val maxPageCount: Int,
    val bookmarksCount: Int,
    val bookmarksBlurbs: List<BookmarksBlurb>,
)
