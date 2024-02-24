package com.soblemprolved.interfaceofourown.features.users.bookmarks

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb

data class UserBookmarksPage(
    val user: String,
    val currentPageCount: Int,
    val maxPageCount: Int,
    val bookmarksCount: Int,
    val bookmarksBlurbs: List<BookmarksBlurb>,
)

