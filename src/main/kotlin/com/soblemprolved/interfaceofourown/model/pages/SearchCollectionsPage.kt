package com.soblemprolved.interfaceofourown.model.pages

import com.soblemprolved.interfaceofourown.model.CollectionBlurb

data class SearchCollectionsPage (
    /**
     * Number of collections matching the filter arguments in the corresponding request.
     */
    val collectionCount: Int,
    val currentPageCount: Int,
    val maxPageCount: Int,

    /**
     * Summary blurbs of the collections matching the filter arguments. As the results are paginated, this will only
     * retrieve the blurbs corresponding to the page specified in the corresponding request.
     */
    val collectionBlurbs: List<CollectionBlurb>
)
