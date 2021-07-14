package service.query

import model.Category
import model.Rating
import model.Warning
import model.WorkFilterParameters
import java.net.URLEncoder

data class WorkFilterQueryMap(
    val workFilterParameters: WorkFilterParameters
): ExplodingRetrofitQueryMap(workFilterParameters.toUnexplodedQueryMap()) {
    companion object {
        private fun WorkFilterParameters.toUnexplodedQueryMap(): Map<String, List<String>> {
            val backingMap: MutableMap<String, List<String>> = mutableMapOf()

            // default parameters
            backingMap["utf8"] = listOf("✓")
            backingMap["commit"] = listOf("Sort+and+Filter")

            // ratings
            val ratingsList = mutableListOf<String>()
            if (showRatingNotRated) ratingsList.add(Rating.NONE.code)
            if (showRatingGeneral) ratingsList.add(Rating.GENERAL.code)
            if (showRatingTeen) ratingsList.add(Rating.TEEN.code)
            if (showRatingMature) ratingsList.add(Rating.MATURE.code)
            if (showRatingExplicit) ratingsList.add(Rating.EXPLICIT.code)
            backingMap["exclude_work_search[rating_ids][]"] = ratingsList

            // warnings - handle both cases
            if (mustContainAllWarnings) {
                val warningsList = mutableListOf<String>()
                if (showWarningChoseNotToUseWarnings) warningsList.add(Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
                if (showWarningNone) warningsList.add(Warning.NO_WARNINGS.code)
                if (showWarningViolence) warningsList.add(Warning.GRAPHIC_VIOLENCE.code)
                if (showWarningCharacterDeath) warningsList.add(Warning.MAJOR_CHARACTER_DEATH.code)
                if (showWarningRape) warningsList.add(Warning.RAPE.code)
                if (showWarningUnderage) warningsList.add(Warning.UNDERAGE.code)
                backingMap["include_work_search[archive_warning_ids][]"] = warningsList
            } else {
                val warningsList = mutableListOf<String>()
                if (!showWarningChoseNotToUseWarnings) warningsList.add(Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
                if (!showWarningNone) warningsList.add(Warning.NO_WARNINGS.code)
                if (!showWarningViolence) warningsList.add(Warning.GRAPHIC_VIOLENCE.code)
                if (!showWarningCharacterDeath) warningsList.add(Warning.MAJOR_CHARACTER_DEATH.code)
                if (!showWarningRape) warningsList.add(Warning.RAPE.code)
                if (!showWarningUnderage) warningsList.add(Warning.UNDERAGE.code)
                backingMap["exclude_work_search[archive_warning_ids][]"] = warningsList
            }

            // categories
            if (mustContainAllCategories) {
                val categoriesList = mutableListOf<String>()
                if (showCategoryGen) categoriesList.add(Category.GEN.code)
                if (showCategoryFM) categoriesList.add(Category.FEMALE_MALE.code)
                if (showCategoryFF) categoriesList.add(Category.FEMALE_FEMALE.code)
                if (showCategoryMM) categoriesList.add(Category.MALE_MALE.code)
                if (showCategoryMulti) categoriesList.add(Category.MULTI.code)
                if (showCategoryOther) categoriesList.add(Category.OTHER.code)
                backingMap["include_work_search[category_ids][]"] = categoriesList
            } else {
                val categoriesList = mutableListOf<String>()
                if (!showCategoryGen) categoriesList.add(Category.GEN.code)
                if (!showCategoryFM) categoriesList.add(Category.FEMALE_MALE.code)
                if (!showCategoryFF) categoriesList.add(Category.FEMALE_FEMALE.code)
                if (!showCategoryMM) categoriesList.add(Category.MALE_MALE.code)
                if (!showCategoryMulti) categoriesList.add(Category.MULTI.code)
                if (!showCategoryOther) categoriesList.add(Category.OTHER.code)
                backingMap["exclude_work_search[category_ids][]"] = categoriesList
            }

            // other bools
            if (showSingleChapterWorksOnly) backingMap["work_search[single_chapter]"] = listOf("1")
            if (showCrossovers xor showNonCrossovers) {
                backingMap["work_search[crossover]"] = if (showCrossovers) listOf("T") else listOf("F")
            }
            if (showCompletedWorks xor showIncompleteWorks) {
                backingMap["work_search[complete]"] = if (showCompletedWorks) listOf("T") else listOf("F")
            }

            // numerical metadata
            // ignore negative values, but try to fulfill the request
            if (hitsMin >= 0 || hitsMax >= 0) {  // if both are negative then use default setting
                backingMap["work_search[hits]"] = when {
                    (hitsMin < 0) -> listOf("<$hitsMax")  // hitsMax is positive
                    (hitsMax < 0) -> listOf(">$hitsMin")  // hitsMin is positive
                    else -> listOf("$hitsMin-$hitsMax")  // ignore legality
                }
            }

            if (kudosMin >= 0 || kudosMax >= 0) {  // if both are negative then use default setting
                backingMap["work_search[kudos_count]"] = when {
                    (kudosMin < 0) -> listOf("<$kudosMax")  // kudosMax is positive
                    (kudosMax < 0) -> listOf(">$kudosMin")  // kudosMin is positive
                    else -> listOf("$kudosMin-$kudosMax")  // ignore legality
                }
            }

            if (commentsMin >= 0 || commentsMax >= 0) {  // if both are negative then use default setting
                backingMap["work_search[comments_count]"] = when {
                    (commentsMin < 0) -> listOf("<$commentsMax")  // commentsMax is positive
                    (commentsMax < 0) -> listOf(">$commentsMin")  // commentsMin is positive
                    else -> listOf("$commentsMin-$commentsMax")  // ignore legality
                }
            }

            if (bookmarksMin >= 0 || bookmarksMax >= 0) {  // if both are negative then use default setting
                backingMap["work_search[bookmarks_count]"] = when {
                    (bookmarksMin < 0) -> listOf("<$bookmarksMax")  // bookmarksMax is positive
                    (bookmarksMax < 0) -> listOf(">$bookmarksMin")  // bookmarksMin is positive
                    else -> listOf("$bookmarksMin-$bookmarksMax")  // ignore legality
                }
            }

            if (wordCountMin >= 0) {
                backingMap["work_search[words_from]"] = listOf(wordCountMin.toString())
            }
            if (wordCountMax >= 0) {
                backingMap["work_search[words_to]"] = listOf(wordCountMax.toString())
            }

            // default toString() uses ISO-8601, which is the format used by AO3 as search params.
            dateUpdatedMin?.let { date ->
                backingMap["work_search[date_from]"] = listOf(date.toString())
            }
            dateUpdatedMax?.let { date ->
                backingMap["work_search[date_to]"] = listOf(date.toString())
            }

            // tags
            backingMap["work_search[other_tag_names]"] = listOf(
                includedTags.joinToString(
                    separator = "%2C",
                    transform = { encodeString(it) }
                )
            )
            backingMap["work_search[excluded_tag_names]"] = listOf(
                excludedTags.joinToString(
                    separator = "%2C",
                    transform = { encodeString(it) }
                )
            )

            // search within works
            if (searchTerm.isNotBlank()) {
                backingMap["work_search[query]"] = listOf(encodeString(searchTerm))
            }

            // language
            backingMap["work_search[language_id]"] = listOf(language.code)

            // sort order
            backingMap["work_search[sort_column]"] = listOf(sortOrder.code)

            return backingMap
        }

        private fun encodeString(tag: String): String {
            return URLEncoder.encode(tag, "UTF-8")
        }
    }
}
