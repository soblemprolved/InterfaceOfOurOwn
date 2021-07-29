package com.soblemprolved.orpheus.model

import java.time.LocalDate

data class SeriesBlurb(
    val id: Long,
    val title: String,
    val authors: List<UserName>,
    val lastUpdatedDate: LocalDate,
    val ratings: List<Rating>,
    val warnings: List<Warning>,
    val categories: List<Category>,
    val fandoms: List<String>,
    val relationships: List<String>,
    val characters: List<String>,
    val freeforms: List<String>,
    val summary: Html,
    val wordCount: Int,
    val workCount: Int,
    val bookmarkCount: Int
)
