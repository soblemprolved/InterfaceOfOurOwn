package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

/**
 * Represents the detailed information of a series.
 * Should be paired with WorkBlurbs when converted from HTML.
 */
data class SeriesInfo(

    /**
     * Unique ID of the series.
     */
    val id: Long,

    /**
     * List of creators of the series.
     */
    val creators: List<UserName>,

    /**
     * Date of creation of the series.
     */
    val beginDate: LocalDate,

    /**
     * Date of last update of the series.
     */
    val lastUpdatedDate: LocalDate,

    /**
     * Raw HTML of the description blurb.
     */
    val description: Html,

    /**
     * Raw HTML of the notes blurb.
     */
    val notes: Html,

    /**
     * Number of words across all works in the series.
     */
    val wordCount: Int,

    /**
     * Number of works in the series.
     */
    val worksCount: Int,

    /**
     * Number of bookmarks across all works in the series.
     */
    val bookmarksCount: Int,

    /**
     * Indicates if the series is complete.
     */
    val completionStatus: Boolean,
)

// multiple creators at https://archiveofourown.org/series/962139
// description and notes at https://archiveofourown.org/series/176438
