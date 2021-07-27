package com.soblemprolved.orpheus.model

import java.time.LocalDate

/**
 * Represents the parameters used to filter works when requesting a list of works associated with a tag.
 * The default [WorkFilterParameters] does not place any restrictions on the works returned; all works will be returned.
 */
data class WorkFilterParameters(
    // ratings do not need include/exclude because a work can only have a single rating
    var showRatingGeneral: Boolean = true,
    var showRatingTeen: Boolean = true,
    var showRatingMature: Boolean = true,
    var showRatingExplicit: Boolean = true,
    var showRatingNotRated: Boolean = true,

    var includeWarningChoseNotToUseWarnings: Boolean = false,
    var includeWarningNone: Boolean = false,
    var includeWarningViolence: Boolean = false,
    var includeWarningCharacterDeath: Boolean = false,
    var includeWarningRape: Boolean = false,
    var includeWarningUnderage: Boolean = false,
    var excludeWarningChoseNotToUseWarnings: Boolean = false,
    var excludeWarningNone: Boolean = false,
    var excludeWarningViolence: Boolean = false,
    var excludeWarningCharacterDeath: Boolean = false,
    var excludeWarningRape: Boolean = false,
    var excludeWarningUnderage: Boolean = false,

    var includeCategoryGen: Boolean = false,
    var includeCategoryFM: Boolean = false,
    var includeCategoryFF: Boolean = false,
    var includeCategoryMM: Boolean = false,
    var includeCategoryMulti: Boolean = false,
    var includeCategoryOther: Boolean = false,
    var excludeCategoryGen: Boolean = false,
    var excludeCategoryFM: Boolean = false,
    var excludeCategoryFF: Boolean = false,
    var excludeCategoryMM: Boolean = false,
    var excludeCategoryMulti: Boolean = false,
    var excludeCategoryOther: Boolean = false,

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
