package com.soblemprolved.interfaceofourown.model.pages

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb
import com.soblemprolved.interfaceofourown.model.CollectionInfo
import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.WorkBlurb

data class CollectionWorksPage(
    val collectionName: String,
    val workBlurbs: List<WorkBlurb>,
    val csrfToken: Csrf
) {
}

data class CollectionBookmarksPage(
    val collectionName: String,
    val bookmarksBlurbs: List<BookmarksBlurb>,
    val csrfToken: Csrf
) {
}