package com.soblemprolved.orpheus.model

import java.time.LocalDate

/**
 * Represents the parameters used to filter works when requesting a list of works associated with a tag.
 * The default [WorkFilterParameters] does not place any restrictions on the works returned; all works will be returned.
 */
data class WorkFilterParameters(
    var showRatingGeneral: Boolean = true,
    var showRatingTeen: Boolean = true,
    var showRatingMature: Boolean = true,
    var showRatingExplicit: Boolean = true,
    var showRatingNotRated: Boolean = true,

    var showWarningChoseNotToUseWarnings: Boolean = true,
    var showWarningNone: Boolean = true,
    var showWarningViolence: Boolean = true,
    var showWarningCharacterDeath: Boolean = true,
    var showWarningRape: Boolean = true,
    var showWarningUnderage: Boolean = true,
    var mustContainAllWarnings: Boolean = false,

    var showCategoryGen: Boolean = true,
    var showCategoryFM: Boolean = true,
    var showCategoryFF: Boolean = true,
    var showCategoryMM: Boolean = true,
    var showCategoryMulti: Boolean = true,
    var showCategoryOther: Boolean = true,
    var mustContainAllCategories: Boolean = false,

    var showSingleChapterWorksOnly: Boolean = false,
    var showCrossovers: Boolean = true,
    var showNonCrossovers: Boolean = true,
    var showCompletedWorks: Boolean = true,
    var showIncompleteWorks: Boolean = true,

    var hitsMin: Int = -1,
    var hitsMax: Int = -1,
    var kudosMin: Int = -1,
    var kudosMax: Int = -1,
    var commentsMin: Int = -1,
    var commentsMax: Int = -1,
    var bookmarksMin: Int = -1,
    var bookmarksMax: Int = -1,
    var wordCountMin: Int = -1,
    var wordCountMax: Int = -1,
    var dateUpdatedMin: LocalDate? = null,
    var dateUpdatedMax: LocalDate? = null,

    var includedTags: MutableList<String> = mutableListOf(),
    var excludedTags: MutableList<String> = mutableListOf(),

    var searchTerm: String = "",
    var language: Language = Language.ALL,
    var sortCriterion: WorkSortCriterion = WorkSortCriterion.DATE_UPDATED
)
