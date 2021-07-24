package com.soblemprolved.orpheus.model

data class BookmarkFilterParameters(
    val sortCriterion: BookmarkSortCriterion,

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
