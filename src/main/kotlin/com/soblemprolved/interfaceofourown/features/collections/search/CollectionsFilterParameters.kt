package com.soblemprolved.interfaceofourown.features.collections.search

import com.soblemprolved.interfaceofourown.model.ChallengeType
import com.soblemprolved.interfaceofourown.model.CollectionsSortCriterion
import com.soblemprolved.interfaceofourown.model.SortDirection
import java.util.AbstractMap.SimpleEntry

/**
 * Represents the additional filter parameters for browsing collections.
 *
 * All the parameters appear in the filter page; browse AO3 for more details. The default values are set to match
 * AO3's defaults.
 */
data class CollectionsFilterParameters(
    val sortCriterion: CollectionsSortCriterion = CollectionsSortCriterion.DATE_CREATED,
    val sortDirection: SortDirection = SortDirection.DESCENDING,
    val titleSearchTerm: String = "",
    val fandom: String = "",
    val isClosed: Boolean? = null,
    val isModerated: Boolean? = null,
    val collectionChallengeType: ChallengeType? = null
)  : AbstractMap<String, String>() {
    override val entries: Set<Map.Entry<String, String>>
        get() {
            val backingSet = mutableSetOf<SimpleEntry<String, String>>()
            val addToSet = { key: String, value: String -> backingSet.add(SimpleEntry(key, value)) }

            addToSet("utf8", "✓")
            addToSet("commit", "Sort and Filter")

            addToSet("sort_column", sortCriterion.code)
            addToSet("sort_direction", sortDirection.code)
            addToSet("collection_filters[title]", titleSearchTerm.ifBlank { "" })
            addToSet("collection_filters[fandom]", fandom.ifBlank { "" })

            isClosed?.let { addToSet("collection_filters[closed]", if (it) "true" else "false") }
            isModerated?.let { addToSet("collection_filters[moderated]", if (it) "true" else "false") }
            collectionChallengeType?.let {
                addToSet("collection_filters[challenge_type]", collectionChallengeType.code)
            }

            return backingSet
        }
}
