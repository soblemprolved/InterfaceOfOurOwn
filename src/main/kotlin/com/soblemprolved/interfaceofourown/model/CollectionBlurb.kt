package com.soblemprolved.interfaceofourown.model

import java.time.LocalDate

data class CollectionBlurb(

    /**
     * Unique ID of the collection.
     */
    val id: String,

    /**
     * Name of the collection.
     */
    val name: String,

    /**
     * Date on which the collection was created.
     */
    val dateCreated: LocalDate,

    /**
     * Indicates if the collection is open for others to add to.
     */
    val isOpen: Boolean,

    /**
     * Indicates if the collection is moderated.
     */
    val isModerated: Boolean,

    /**
     * Indicates if the collection is revealed or not.
     */
    val isRevealed: Boolean,

    /**
     * Indicates if the collection is anonymous.
     */
    val isAnonymous: Boolean,

    /**
     * Challenge type of the collection.
     */
    val challenge: ChallengeType,

    /**
     * Raw HTML of the summary of the collection.
     */
    val summary: Html,

    /**
     * List of users maintaining the collection.
     */
    val maintainers: List<UserReference>
)
