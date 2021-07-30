package com.soblemprolved.orpheus.service.query

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.model.Category
import com.soblemprolved.orpheus.model.Rating
import com.soblemprolved.orpheus.model.Warning

class BookmarkFilterQueryMap(
    val bookmarkFilterParameters: BookmarkFilterParameters
) : ExplodingQueryMap(bookmarkFilterParameters.toUnexplodedQueryMap()) {
    companion object {
        private fun BookmarkFilterParameters.toUnexplodedQueryMap(): Map<String, List<String>> {
            val backingMap: MutableMap<String, List<String>> = mutableMapOf()

            // default parameters
            backingMap["utf8"] = listOf("✓")
            backingMap["commit"] = listOf("Sort and Filter")

            // ratings
            val includedRatingsList = mutableListOf<String>()
            if (includeRatingNotRated) includedRatingsList.add(Rating.NONE.code)
            if (includeRatingGeneral) includedRatingsList.add(Rating.GENERAL.code)
            if (includeRatingTeen) includedRatingsList.add(Rating.TEEN.code)
            if (includeRatingMature) includedRatingsList.add(Rating.MATURE.code)
            if (includeRatingExplicit) includedRatingsList.add(Rating.EXPLICIT.code)
            backingMap["include_bookmark_search[rating_ids][]"] = includedRatingsList

            val excludedRatingsList = mutableListOf<String>()
            if (excludeRatingNotRated) excludedRatingsList.add(Rating.NONE.code)
            if (excludeRatingGeneral) excludedRatingsList.add(Rating.GENERAL.code)
            if (excludeRatingTeen) excludedRatingsList.add(Rating.TEEN.code)
            if (excludeRatingMature) excludedRatingsList.add(Rating.MATURE.code)
            if (excludeRatingExplicit) excludedRatingsList.add(Rating.EXPLICIT.code)
            backingMap["exclude_bookmark_search[rating_ids][]"] = excludedRatingsList

            // warnings - handle both cases
            val includedWarningsList = mutableListOf<String>()
            if (includeWarningChoseNotToUseWarnings) includedWarningsList.add(Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (includeWarningNone) includedWarningsList.add(Warning.NO_WARNINGS.code)
            if (includeWarningViolence) includedWarningsList.add(Warning.GRAPHIC_VIOLENCE.code)
            if (includeWarningCharacterDeath) includedWarningsList.add(Warning.MAJOR_CHARACTER_DEATH.code)
            if (includeWarningRape) includedWarningsList.add(Warning.RAPE.code)
            if (includeWarningUnderage) includedWarningsList.add(Warning.UNDERAGE.code)
            backingMap["include_bookmark_search[archive_warning_ids][]"] = includedWarningsList

            val excludedWarningsList = mutableListOf<String>()
            if (excludeWarningChoseNotToUseWarnings) excludedWarningsList.add(Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (excludeWarningNone) excludedWarningsList.add(Warning.NO_WARNINGS.code)
            if (excludeWarningViolence) excludedWarningsList.add(Warning.GRAPHIC_VIOLENCE.code)
            if (excludeWarningCharacterDeath) excludedWarningsList.add(Warning.MAJOR_CHARACTER_DEATH.code)
            if (excludeWarningRape) excludedWarningsList.add(Warning.RAPE.code)
            if (excludeWarningUnderage) excludedWarningsList.add(Warning.UNDERAGE.code)
            backingMap["exclude_bookmark_search[archive_warning_ids][]"] = excludedWarningsList

            // categories
            val includedCategoriesList = mutableListOf<String>()
            if (includeCategoryGen) includedCategoriesList.add(Category.GEN.code)
            if (includeCategoryFM) includedCategoriesList.add(Category.FEMALE_MALE.code)
            if (includeCategoryFF) includedCategoriesList.add(Category.FEMALE_FEMALE.code)
            if (includeCategoryMM) includedCategoriesList.add(Category.MALE_MALE.code)
            if (includeCategoryMulti) includedCategoriesList.add(Category.MULTI.code)
            if (includeCategoryOther) includedCategoriesList.add(Category.OTHER.code)
            backingMap["include_bookmark_search[category_ids][]"] = includedCategoriesList

            val excludedCategoriesList = mutableListOf<String>()
            if (excludeCategoryGen) excludedCategoriesList.add(Category.GEN.code)
            if (excludeCategoryFM) excludedCategoriesList.add(Category.FEMALE_MALE.code)
            if (excludeCategoryFF) excludedCategoriesList.add(Category.FEMALE_FEMALE.code)
            if (excludeCategoryMM) excludedCategoriesList.add(Category.MALE_MALE.code)
            if (excludeCategoryMulti) excludedCategoriesList.add(Category.MULTI.code)
            if (excludeCategoryOther) excludedCategoriesList.add(Category.OTHER.code)
            backingMap["exclude_bookmark_search[category_ids][]"] = excludedCategoriesList

            // tags
            backingMap["bookmark_search[other_tag_names]"] = listOf(
                includedWorkTags.joinToString(separator = ",")
            )
            backingMap["bookmark_search[other_bookmark_tag_names]"] = listOf(
                includedBookmarkTags.joinToString(separator = ",")
            )

            backingMap["bookmark_search[excluded_tag_names]"] = listOf(
                excludedWorkTags.joinToString(separator = ",")
            )
            backingMap["bookmark_search[excluded_bookmark_tag_names]"] = listOf(
                excludedBookmarkTags.joinToString(separator = ",")
            )

            // search within works
            if (searchTerm.isNotBlank()) {
                backingMap["bookmark_search[bookmarkable_query]"] = listOf(searchTerm)
            }
            if (bookmarkSearchTerm.isNotBlank()) {
                backingMap["bookmark_search[bookmark_query]"] = listOf(searchTerm)
            }

            // apparently when any of these two bools are true, AO3 includes both 0 and 1 for some reason
            // i suspect there is no need for that
            if (showRecommendationsOnly) {
                backingMap["bookmark_search[rec]"] = listOf("1")
            }

            if (showBookmarksWithNotesOnly) {
                backingMap["bookmark_search[with_notes]"] = listOf("1")
            }

            // language
            backingMap["bookmark_search[language_id]"] = listOf(language.code)

            // sorting criteria
            backingMap["bookmark_search[sort_column]"] = listOf(sortCriterion.code)

            return backingMap
        }
    }
}