package com.soblemprolved.interfaceofourown.features.autocomplete

data class AutocompletePage(
    /**
     * List of up results of the requested type that match the search term.
     * The maximum number of results that can be returned is 15.
     */
    val autocompleteResults: List<String>
)
