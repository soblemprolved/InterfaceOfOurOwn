package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class SeriesBlurb(

    /**
     * Unique ID of the series.
     */
    val id: Long,

    /**
     * Title of the series.
     */
    val title: String,

    /**
     * List of authors of the series.
     */
    val authors: List<UserReference>,

    /**
     * Date of the most recent update to this series.
     */
    val lastUpdatedDate: LocalDate,

    /**
     * Content ratings of the works in the series.
     */
    val ratings: List<Rating>,

    /**
     * Warnings found in the works of the series.
     */
    val warnings: List<Warning>,

    /**
     * Categories of the relationships found in the works of the series.
     */
    val categories: List<Category>,

    /**
     * Fandom tags associated with the works of the series.
     */
    val fandoms: List<String>,

    /**
     * Relationship tags associated with the works of the series.
     */
    val relationships: List<String>,

    /**
     * Character tags associated with the works of the series.
     */
    val characters: List<String>,

    /**
     * Freeform tags found in the works of the series.
     */
    val freeforms: List<String>,

    /**
     * Raw HTML of the summary.
     */
    val summary: Html,

    /**
     * Total number of words across all works in the series.
     */
    val wordCount: Int,

    /**
     * Number of works in the series.
     */
    val workCount: Int,

    /**
     * Total number of bookmarks across all works in the series.
     */
    val bookmarkCount: Int
)
