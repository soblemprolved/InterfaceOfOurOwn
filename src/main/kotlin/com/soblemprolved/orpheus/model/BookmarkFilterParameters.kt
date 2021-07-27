package com.soblemprolved.orpheus.model

data class BookmarkFilterParameters(
    var sortCriterion: BookmarkSortCriterion = BookmarkSortCriterion.DATE_BOOKMARKED,

    // as bookmarks include series which can contain multiple ratings, i will split this into include/exclude
    var includeRatingGeneral: Boolean = true,
    var includeRatingTeen: Boolean = true,
    var includeRatingMature: Boolean = true,
    var includeRatingExplicit: Boolean = true,
    var includeRatingNotRated: Boolean = true,
    var excludeRatingGeneral: Boolean = false,
    var excludeRatingTeen: Boolean = false,
    var excludeRatingMature: Boolean = false,
    var excludeRatingExplicit: Boolean = false,
    var excludeRatingNotRated: Boolean = false,

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

    var includedWorkTags: MutableList<String> = mutableListOf(),
    var excludedWorkTags: MutableList<String> = mutableListOf(),
    var includedBookmarkTags: MutableList<String> = mutableListOf(),
    var excludedBookmarkTags: MutableList<String> = mutableListOf(),

    var searchTerm: String = "",
    var bookmarkSearchTerm: String = "",
    var language: Language = Language.ALL,

    val showRecommendationsOnly: Boolean = false,
    val showBookmarksWithNotesOnly: Boolean = false
)
