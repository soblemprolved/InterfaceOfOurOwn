package com.soblemprolved.interfaceofourown.model.pages

import com.soblemprolved.interfaceofourown.model.BookmarksBlurb
import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.WorkBlurb

data class TagWorksPage(
    /**
     * Name of the tag.
     */
    val tag: String,
    val currentPageCount: Int,
    val maxPageCount: Int,

    /**
     * Number of works associated with the tag.
     */
    val worksCount: Int,

    /**
     * Summary blurbs of the works associated with the tag. As the results are paginated, this will only
     * retrieve the blurbs corresponding to the page specified in the corresponding request.
     */
    val workBlurbs: List<WorkBlurb>,
)

data class TagBookmarksPage(
    val tag: String,
    val currentPageCount: Int,
    val maxPageCount: Int,
    val bookmarksCount: Int,
    val bookmarksBlurbs: List<BookmarksBlurb>,
)
