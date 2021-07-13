package model

import model.SortOrder
import java.time.LocalDate

data class WorkFilterParameters(
    var showRatingGeneral: Boolean = true,
    var showRatingTeen: Boolean = true,
    var showRatingMature: Boolean = true,
    var showRatingExplicit: Boolean = true,
    var showRatingNotRated: Boolean = true,

    var showWarningNone: Boolean = true,
    var showWarningViolence: Boolean = true,
    var showWarningCharacterDeath: Boolean = true,
    var showWarningUnderage: Boolean = true,
    var showWarningRape: Boolean = true,
    var showWarningChoseNoWarnings: Boolean = true,
    var mustContainAllWarnings: Boolean = false,

    var showCategoryGen: Boolean = true,
    var showCategoryFM: Boolean = true,
    var showCategoryFF: Boolean = true,
    var showCategoryMM: Boolean = true,
    var showCategoryMulti: Boolean = true,
    var showCategoryOther: Boolean = true,

    var showSingleChapterWorksOnly: Boolean = false,
    var showCrossovers: Boolean = true,
    var showNonCrossovers: Boolean = true,
    var showCompletedWorks: Boolean = true,
    var showIncompleteWorks: Boolean = true,

    var hitsMin: Int = 0,
    var hitsMax: Int = 0,
    var kudosMin: Int = 0,
    var kudosMax: Int = 0,
    var commentsMin: Int = 0,
    var commentsMax: Int = 0,
    var bookmarksMin: Int = 0,
    var bookmarksMax: Int = 0,
    var wordCountMin: Int = 0,
    var wordCountMax: Int = 0,
    var dateUpdatedMin: LocalDate? = null,
    var dateUpdatedMax: LocalDate? = null,

    var includedTags: MutableList<String> = mutableListOf<String>(),
    var excludedTags: MutableList<String> = mutableListOf<String>(),

    var searchTerm: String = "",
    var language: String = "",
    var sortOrder: SortOrder = SortOrder.DATE_UPDATED
)
