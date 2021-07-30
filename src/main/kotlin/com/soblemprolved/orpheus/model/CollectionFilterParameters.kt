package com.soblemprolved.orpheus.model

data class CollectionFilterParameters(
    val sortCriterion: CollectionsSortCriterion = CollectionsSortCriterion.DATE_CREATED,
    val sortDirection: SortDirection = SortDirection.DESCENDING,
    val titleSearchTerm: String = "",
    val fandom: String = "",
    val isClosed: Boolean? = null,
    val isModerated: Boolean? = null,
    val collectionChallengeType: ChallengeType? = null
)
