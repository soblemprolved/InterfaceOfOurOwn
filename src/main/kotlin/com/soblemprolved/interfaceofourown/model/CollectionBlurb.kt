package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class CollectionBlurb(
    val id: String,
    val name: String,
    val dateCreated: LocalDate,
    val isOpen: Boolean,
    val isModerated: Boolean,
    val isUnrevealed: Boolean,
    val isAnonymous: Boolean,
    val challenge: ChallengeType,
    val summary: Html,
    val maintainers: List<UserName>
)
