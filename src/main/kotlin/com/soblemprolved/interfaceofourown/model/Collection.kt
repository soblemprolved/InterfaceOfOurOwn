package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

// TODO: start at profile section of collection - add in more fields, esp related to challenges
data class Collection(
    val id: String,
    val name: String,
    val dateCreated: LocalDate,
    val isOpen: Boolean,
    val isModerated: Boolean,
    val isUnrevealed: Boolean,
    val isAnonymous: Boolean,
    val challenge: ChallengeType,
    val summary: Html,
    val maintainers: List<UserName>,
    val subcollectionCount: Int,
    val fandomCount: Int,
    val workCount: Int,
    val bookmarkedItemCount: Int
)
