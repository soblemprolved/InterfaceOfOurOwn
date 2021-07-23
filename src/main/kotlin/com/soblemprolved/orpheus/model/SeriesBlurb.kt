package com.soblemprolved.orpheus.model

import java.time.LocalDate

data class SeriesBlurb(
    val id: Long,
    val title: String,
    val authors: List<User>,     // convert to username?
    val lastUpdatedDate: LocalDate,    // convert to java date
    val fandoms: List<String>,
    val relationships: List<String>,
    val characters: List<String>,
    val freeforms: List<String>,
    val wordCount: Int,
    val worksCount: Int
)
