package com.soblemprolved.orpheus.model

data class Collection(
    val id: String,
    val name: String,
    val isOpen: Boolean,
    val isModerated: Boolean,
    val isRevealed: Boolean,
    val isAnonymous: Boolean,
    val challenge: ChallengeType,
    val summary: Html,
    val maintainers: List<User>,
    val subcollectionCount: Int,
    val fandomCount: Int,
    val workCount: Int,
    val bookmarkedItemCount: Int
) {
    enum class ChallengeType {
        GIFT, PROMPT, NONE
    }
}