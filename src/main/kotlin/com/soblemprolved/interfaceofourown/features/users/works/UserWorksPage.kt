package com.soblemprolved.interfaceofourown.features.users.works

import com.soblemprolved.interfaceofourown.model.WorkBlurb

data class UserWorksPage(
    /**
     * Name of the tag.
     */
    val user: String,
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
