package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

// TODO: start at profile section of collection - add in more fields, esp related to challenges
data class CollectionInfo(
    /**
     * The unique ID of the collection.
     */
    val id: String,

    /**
     * The name of the collection.
     */
    val name: String,

    /**
     * The date on which the collection was created.
     */
    val dateCreated: LocalDate,

    /**
     * Indicates if the collection is open.
     */
    val isOpen: Boolean,

    /**
     * Indicates if the collection is moderated.
     */
    val isModerated: Boolean,

    /**
     * Indicates if the collection is unrevealed.
     */
    val isUnrevealed: Boolean,

    /**
     * Indicates if the collection is anonymous.
     */
    val isAnonymous: Boolean,

    /**
     * Indicates the challenge type of the collection.
     */
    val challenge: ChallengeType,

    /**
     * Raw HTML of the summary of the collection.
     */
    val summary: Html,

    /**
     * Maintainers of the collection.
     */
    val maintainers: List<UserReference>,

    /**
     * Number of subcollections of this collection.
     */
    val subcollectionCount: Int,

    /**
     * Number of fandoms appearing in the works of this collection.
     */
    val fandomCount: Int,

    /**
     * Number of works in this collection.
     */
    val workCount: Int,

    /**
     * Number of items bookmarked by this collection.
     */
    val bookmarkedItemCount: Int
)
