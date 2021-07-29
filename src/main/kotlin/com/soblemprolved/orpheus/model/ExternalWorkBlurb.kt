package com.soblemprolved.orpheus.model

import java.time.LocalDate

data class ExternalWorkBlurb(
    val id: Long,
    val title: String,
    val authors: List<User>,
    val lastUpdatedDate: LocalDate,
    val rating: Rating,
    val categories: List<Category>,
    val fandoms: List<String>,  // leave tags as strings
    val relationships: List<String>,
    val characters: List<String>,
    val freeforms: List<String>,
    val summary: Html,
    val bookmarkCount: Int
) {
    val warning = Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS
}
