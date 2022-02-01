package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class Bookmark(

    /**
     * User associated with the bookmark
     */
    val userReference: UserReference,

    /**
     * List of tags associated with the bookmark
     */
    val tags: List<String>,

    /**
     * List of collections which the bookmark was added to.
     */
    val collections: List<CollectionReference>,

    /**
     * Time at which the bookmark was created.
     */
    val date: LocalDate,

    /**
     * Bookmarker's notes in raw HTML.
     */
    val notes: Html,

    /**
     * Type of the bookmark.
     */
    val bookmarkType: BookmarkType
)
