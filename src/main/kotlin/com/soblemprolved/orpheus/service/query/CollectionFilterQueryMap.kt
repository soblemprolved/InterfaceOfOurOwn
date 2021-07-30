package com.soblemprolved.orpheus.service.query

import com.soblemprolved.orpheus.model.CollectionFilterParameters

class CollectionFilterQueryMap(
    val collectionFilterParameters: CollectionFilterParameters
) : ExplodingQueryMap(collectionFilterParameters.toUnexplodedQueryMap()) {
    companion object {
        private fun CollectionFilterParameters.toUnexplodedQueryMap(): Map<String, List<String>> {
            val backingMap: MutableMap<String, List<String>> = mutableMapOf()

            backingMap["utf8"] = listOf("✓")
            backingMap["commit"] = listOf("Sort and Filter")  // okhttp converts spaces to +

            backingMap["sort_column"] = listOf(sortCriterion.code)
            backingMap["sort_direction"] = listOf(sortDirection.code)
            backingMap["collection_filters[title]"] = listOf(titleSearchTerm.ifBlank { "" })
            backingMap["collection_filters[fandom]"] = listOf(fandom.ifBlank { "" })

            isClosed?.let {
                backingMap["collection_filters[closed]"] = listOf(if (isClosed) "true" else "false")
            }
            isModerated?.let {
                backingMap["collection_filters[moderated]"] = listOf(if (isModerated) "true" else "false")
            }
            collectionChallengeType?.let {
                backingMap["collection_filters[challenge_type]"] = listOf(collectionChallengeType.code)
            }

            return backingMap
        }
    }
}
