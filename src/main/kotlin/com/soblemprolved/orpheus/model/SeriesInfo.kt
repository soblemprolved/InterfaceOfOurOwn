package com.soblemprolved.orpheus.model

import java.time.LocalDate

/**
 * Represents the detailed information of a series.
 * Should be paired with WorkBlurbs when converted from HTML.
 */
data class SeriesInfo(
    val id: Long, // can this be a long?
    val creators: List<UserName>,
    val beginDate: LocalDate,
    val lastUpdatedDate: LocalDate,
    val description: Html,
    val notes: Html,
    val wordCount: Int,
    val worksCount: Int,
    val bookmarksCount: Int,
    val completionStatus: Boolean,
)

// multiple creators at https://archiveofourown.org/series/962139
// description and notes at https://archiveofourown.org/series/176438
