package com.soblemprolved.interfaceofourown.features.tags.works

import com.soblemprolved.interfaceofourown.model.*
import java.time.LocalDate
import java.util.AbstractMap.SimpleEntry

/**
 * Represents the parameters used to filter works when browsing works by tag. The default values match AO3's defaults.
 *
 * Most parameters that appear in AO3's filter dialog appear here. The only exceptions are for ratings; the
 * include/exclude rating options were merged together into one showRating<name> for each rating, as it makes no sense
 * to include and exclude ratings for works.
 *
 * Additional parameters for filtering works are also available, and they will be marked as such in the documentation.
 */
data class TagWorksFilterParameters(
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

    /**
     * Additional parameter.
     *
     * Determines if the results should be restricted to oneshots only.
     */
    var showSingleChapterWorksOnly: Boolean = false,
    var showCrossovers: Boolean? = null,
    var showCompletedWorks: Boolean? = null,

    /**
     * Additional parameter.
     */
    var hitsMin: Int = -1,

    /**
     * Additional parameter.
     */
    var hitsMax: Int = -1,

    /**
     * Additional parameter.
     */
    var kudosMin: Int = -1,

    /**
     * Additional parameter.
     */
    var kudosMax: Int = -1,

    /**
     * Additional parameter.
     */
    var commentsMin: Int = -1,

    /**
     * Additional parameter.
     */
    var commentsMax: Int = -1,

    /**
     * Additional parameter.
     */
    var bookmarksMin: Int = -1,

    /**
     * Additional parameter.
     */
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
) : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            val backingSet = mutableSetOf<SimpleEntry<String, String>>()
            val addToSet = { key: String, value: String -> backingSet.add(SimpleEntry(key, value)) }

            // default parameters
            addToSet("utf8", "✓")
            addToSet("commit", "Sort and Filter")

            // ratings
            if (!showRatingNotRated)    addToSet("exclude_work_search[rating_ids][]", Rating.NONE.code)
            if (!showRatingGeneral)     addToSet("exclude_work_search[rating_ids][]", Rating.GENERAL.code)
            if (!showRatingTeen)        addToSet("exclude_work_search[rating_ids][]", Rating.TEEN.code)
            if (!showRatingMature)      addToSet("exclude_work_search[rating_ids][]", Rating.MATURE.code)
            if (!showRatingExplicit)    addToSet("exclude_work_search[rating_ids][]", Rating.EXPLICIT.code)

            // warnings - handle both cases
            if (includeWarningChoseNotToUseWarnings)
                addToSet("include_work_search[archive_warning_ids][]", Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (includeWarningNone)
                addToSet("include_work_search[archive_warning_ids][]", Warning.NO_WARNINGS.code)
            if (includeWarningViolence)
                addToSet("include_work_search[archive_warning_ids][]", Warning.GRAPHIC_VIOLENCE.code)
            if (includeWarningCharacterDeath)
                addToSet("include_work_search[archive_warning_ids][]", Warning.MAJOR_CHARACTER_DEATH.code)
            if (includeWarningRape)
                addToSet("include_work_search[archive_warning_ids][]", Warning.RAPE.code)
            if (includeWarningUnderage)
                addToSet("include_work_search[archive_warning_ids][]", Warning.UNDERAGE.code)

            if (excludeWarningChoseNotToUseWarnings)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.CREATOR_CHOSE_NOT_TO_USE_WARNINGS.code)
            if (excludeWarningNone)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.NO_WARNINGS.code)
            if (excludeWarningViolence)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.GRAPHIC_VIOLENCE.code)
            if (excludeWarningCharacterDeath)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.MAJOR_CHARACTER_DEATH.code)
            if (excludeWarningRape)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.RAPE.code)
            if (excludeWarningUnderage)
                addToSet("exclude_work_search[archive_warning_ids][]", Warning.UNDERAGE.code)

            // categories
            if (includeCategoryGen)     addToSet("include_work_search[category_ids][]", Category.GEN.code)
            if (includeCategoryFM)      addToSet("include_work_search[category_ids][]", Category.FEMALE_MALE.code)
            if (includeCategoryFF)      addToSet("include_work_search[category_ids][]", Category.FEMALE_FEMALE.code)
            if (includeCategoryMM)      addToSet("include_work_search[category_ids][]", Category.MALE_MALE.code)
            if (includeCategoryMulti)   addToSet("include_work_search[category_ids][]", Category.MULTI.code)
            if (includeCategoryOther)   addToSet("include_work_search[category_ids][]", Category.OTHER.code)

            if (excludeCategoryGen)     addToSet("exclude_work_search[category_ids][]", Category.GEN.code)
            if (excludeCategoryFM)      addToSet("exclude_work_search[category_ids][]", Category.FEMALE_MALE.code)
            if (excludeCategoryFF)      addToSet("exclude_work_search[category_ids][]", Category.FEMALE_FEMALE.code)
            if (excludeCategoryMM)      addToSet("exclude_work_search[category_ids][]", Category.MALE_MALE.code)
            if (excludeCategoryMulti)   addToSet("exclude_work_search[category_ids][]", Category.MULTI.code)
            if (excludeCategoryOther)   addToSet("exclude_work_search[category_ids][]", Category.OTHER.code)

            // other bools
            if (showSingleChapterWorksOnly) addToSet("work_search[single_chapter]", "1")
            showCrossovers?.let { addToSet("work_search[crossover]", if (it) "T" else "F") }
            showCompletedWorks?.let { addToSet("work_search[complete]", if (it) "T" else "F") }

            // numerical metadata
            // ignore negative values, but try to fulfill the request
            if (hitsMin >= 0 || hitsMax >= 0) {  // if both are negative then use default setting
                addToSet(
                    "work_search[hits]",
                    when {
                        hitsMin < 0 -> "<$hitsMax"          // hitsMax is positive
                        hitsMax < 0 -> ">$hitsMin"          // hitsMin is positive
                        else ->        "$hitsMin-$hitsMax"  // ignore legality
                    }
                )
            }

            if (kudosMin >= 0 || kudosMax >= 0) {  // if both are negative then use default setting
                addToSet(
                    "work_search[kudos_count]",
                    when {
                        kudosMin < 0 -> "<$kudosMax"            // kudosMax is positive
                        kudosMax < 0 -> ">$kudosMin"            // kudosMin is positive
                        else ->         "$kudosMin-$kudosMax"   // ignore legality
                    }
                )
            }

            if (commentsMin >= 0 || commentsMax >= 0) {  // if both are negative then use default setting
                addToSet(
                    "work_search[comments_count]",
                    when {
                        commentsMin < 0 -> "<$commentsMax"              // commentsMax is positive
                        commentsMax < 0 -> ">$commentsMin"              // commentsMin is positive
                        else ->            "$commentsMin-$commentsMax"  // ignore legality
                    }
                )
            }

            if (bookmarksMin >= 0 || bookmarksMax >= 0) {  // if both are negative then use default setting
                addToSet(
                    "work_search[bookmarks_count]",
                    when {
                        bookmarksMin < 0 -> "<$bookmarksMax"                // bookmarksMax is positive
                        bookmarksMax < 0 -> ">$bookmarksMin"                // bookmarksMin is positive
                        else ->             "$bookmarksMin-$bookmarksMax"   // ignore legality
                    }
                )
            }

            if (wordCountMin >= 0) addToSet("work_search[words_from]", wordCountMin.toString())
            if (wordCountMax >= 0) addToSet("work_search[words_to]", wordCountMax.toString())

            // default toString() uses ISO-8601, which is the format used by AO3 as search params.
            dateUpdatedMin?.let { date -> addToSet("work_search[date_from]", date.toString()) }
            dateUpdatedMax?.let { date -> addToSet("work_search[date_to]", date.toString()) }

            // tags
            addToSet("work_search[other_tag_names]", includedTags.joinToString(separator = ","))
            addToSet("work_search[excluded_tag_names]", excludedTags.joinToString(separator = ","))

            // search within works
            if (searchTerm.isNotBlank()) addToSet("work_search[query]", searchTerm)

            // language
            addToSet("work_search[language_id]", language.code)

            // sort order
            addToSet("work_search[sort_column]", sortCriterion.code)

            return backingSet
        }
}
