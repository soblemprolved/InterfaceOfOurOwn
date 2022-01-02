package com.soblemprolved.interfaceofourown.model

import java.util.AbstractMap.SimpleEntry

// TODO: should I move parameter classes to service/models?
data class BookmarkFilterParameters(
    var sortCriterion: BookmarkSortCriterion = BookmarkSortCriterion.DATE_BOOKMARKED,

    // as bookmarks include series which can contain multiple ratings, this will be split into include/exclude
    var includeRatingGeneral: Boolean = false,
    var includeRatingTeen: Boolean = false,
    var includeRatingMature: Boolean = false,
    var includeRatingExplicit: Boolean = false,
    var includeRatingNotRated: Boolean = false,
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
) : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            val backingSet = mutableSetOf<SimpleEntry<String, String>>()
            val addToSet = { key: String, value: String -> backingSet.add(SimpleEntry(key, value)) }

            // default parameters
            addToSet("utf8", "✓")
            addToSet("commit", "Sort and Filter")

            // ratings
            if (includeRatingNotRated)  addToSet("include_bookmark_search[rating_ids][]", Rating.NONE.code)
            if (includeRatingGeneral)   addToSet("include_bookmark_search[rating_ids][]", Rating.GENERAL.code)
            if (includeRatingTeen)      addToSet("include_bookmark_search[rating_ids][]", Rating.TEEN.code)
            if (includeRatingMature)    addToSet("include_bookmark_search[rating_ids][]", Rating.MATURE.code)
            if (includeRatingExplicit)  addToSet("include_bookmark_search[rating_ids][]", Rating.EXPLICIT.code)

            if (excludeRatingNotRated)  addToSet("exclude_bookmark_search[rating_ids][]", Rating.NONE.code)
            if (excludeRatingGeneral)   addToSet("exclude_bookmark_search[rating_ids][]", Rating.GENERAL.code)
            if (excludeRatingTeen)      addToSet("exclude_bookmark_search[rating_ids][]", Rating.TEEN.code)
            if (excludeRatingMature)    addToSet("exclude_bookmark_search[rating_ids][]", Rating.MATURE.code)
            if (excludeRatingExplicit)  addToSet("exclude_bookmark_search[rating_ids][]", Rating.EXPLICIT.code)
            
            // warnings
            if (includeWarningChoseNotToUseWarnings)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (includeWarningNone)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.NO_WARNINGS.code)
            if (includeWarningViolence)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.GRAPHIC_VIOLENCE.code)
            if (includeWarningCharacterDeath)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.MAJOR_CHARACTER_DEATH.code)
            if (includeWarningRape)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.RAPE.code)
            if (includeWarningUnderage)
                addToSet("include_bookmark_search[archive_warning_ids][]", Warning.UNDERAGE.code)

            if (excludeWarningChoseNotToUseWarnings)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (excludeWarningNone)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.NO_WARNINGS.code)
            if (excludeWarningViolence)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.GRAPHIC_VIOLENCE.code)
            if (excludeWarningCharacterDeath)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.MAJOR_CHARACTER_DEATH.code)
            if (excludeWarningRape)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.RAPE.code)
            if (excludeWarningUnderage)
                addToSet("exclude_bookmark_search[archive_warning_ids][]", Warning.UNDERAGE.code)

            // categories
            if (includeCategoryGen)     addToSet("include_bookmark_search[category_ids][]", Category.GEN.code)
            if (includeCategoryFM)      addToSet("include_bookmark_search[category_ids][]", Category.FEMALE_MALE.code)
            if (includeCategoryFF)      addToSet("include_bookmark_search[category_ids][]", Category.FEMALE_FEMALE.code)
            if (includeCategoryMM)      addToSet("include_bookmark_search[category_ids][]", Category.MALE_MALE.code)
            if (includeCategoryMulti)   addToSet("include_bookmark_search[category_ids][]", Category.MULTI.code)
            if (includeCategoryOther)   addToSet("include_bookmark_search[category_ids][]", Category.OTHER.code)

            if (excludeCategoryGen)     addToSet("exclude_bookmark_search[category_ids][]", Category.GEN.code)
            if (excludeCategoryFM)      addToSet("exclude_bookmark_search[category_ids][]", Category.FEMALE_MALE.code)
            if (excludeCategoryFF)      addToSet("exclude_bookmark_search[category_ids][]", Category.FEMALE_FEMALE.code)
            if (excludeCategoryMM)      addToSet("exclude_bookmark_search[category_ids][]", Category.MALE_MALE.code)
            if (excludeCategoryMulti)   addToSet("exclude_bookmark_search[category_ids][]", Category.MULTI.code)
            if (excludeCategoryOther)   addToSet("exclude_bookmark_search[category_ids][]", Category.OTHER.code)

            // tags
            addToSet("bookmark_search[other_tag_names]", includedWorkTags.joinToString(separator = ","))
            addToSet("bookmark_search[other_bookmark_tag_names]", includedBookmarkTags.joinToString(separator = ","))
            addToSet("bookmark_search[excluded_tag_names]", excludedWorkTags.joinToString(separator = ","))
            addToSet("bookmark_search[excluded_bookmark_tag_names]", excludedBookmarkTags.joinToString(separator = ","))

            // search within works
            if (searchTerm.isNotBlank())            addToSet("bookmark_search[bookmarkable_query]", searchTerm)
            if (bookmarkSearchTerm.isNotBlank())    addToSet("bookmark_search[bookmark_query]", bookmarkSearchTerm)

            // apparently when any of these two bools are true, AO3 includes both 0 and 1 for some reason
            // I suspect there is no need for that
            if (showRecommendationsOnly)            addToSet("bookmark_search[rec]", "1")
            if (showBookmarksWithNotesOnly)         addToSet("bookmark_search[with_notes]", "1")

            // language
            addToSet("bookmark_search[language_id]", language.code)

            // sorting criteria
            addToSet("bookmark_search[sort_column]", sortCriterion.code)

            return backingSet
        }
}
