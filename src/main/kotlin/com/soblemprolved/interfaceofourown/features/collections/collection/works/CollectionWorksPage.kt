package com.soblemprolved.interfaceofourown.features.collections.collection.works

import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.WorkBlurb

data class CollectionWorksPage(
    val collectionName: String,
    val workBlurbs: List<WorkBlurb>,
    val csrfToken: Csrf
) {
}