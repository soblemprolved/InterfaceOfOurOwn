package com.soblemprolved.orpheus.model

data class CollectionsFilterParameters(
    val sortCriterion: CollectionsSortCriterion,
    val sortDirection: SortDirection,
    val titleSearchTerm: String,
    val fandom: String?,
    val isClosed: Boolean?,
    val isModerated: Boolean?,
    val collectionChallengeType: ChallengeType?
)
