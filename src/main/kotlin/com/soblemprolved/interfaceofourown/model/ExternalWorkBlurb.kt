package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class ExternalWorkBlurb(

    /**
     * The unique ID of the entire work.
     */
    val id: Long,

    /**
     * The title of the work.
     */
    val title: String,

    /**
     * The list of authors of the work.
     */
    val authors: List<UserReference>,

    /**
     * The date of the most recent update to this work.
     */
    val lastUpdatedDate: LocalDate,

    /**
     * The content rating of this work (e.g. General).
     */
    val rating: Rating,

    /**
     * The categories of the relationships featured in this work.
     */
    val categories: List<Category>,

    /**
     * The fandom tags associated with this work.
     */
    val fandoms: List<String>,  // leave tags as strings

    /**
     * The relationship tags associated with this work.
     */
    val relationships: List<String>,

    /**
     * The character tags associated with this work.
     */
    val characters: List<String>,

    /**
     * The character tags associated with this work.
     */
    val freeforms: List<String>,

    /**
     * The raw HTML of the summary.
     */
    val summary: Html,

    /**
     * The number of bookmarks that this work has received.
     */
    val bookmarkCount: Int
) {

    /**
     * The content warnings for the work (e.g. Graphic Depictions of Violence).
     */
    val warnings = listOf(Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS)
}
