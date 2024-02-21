package com.soblemprolved.interfaceofourown.features.collections.collection.bookmarks

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb
import com.soblemprolved.interfaceofourown.model.Csrf

data class CollectionBookmarksPage(
    val collectionName: String,
    val bookmarksBlurbs: List<BookmarksBlurb>,
    val csrfToken: Csrf
)
