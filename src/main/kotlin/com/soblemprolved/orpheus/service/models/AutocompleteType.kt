package com.soblemprolved.orpheus.service.models

enum class AutocompleteType(
    val pathSegment: String
) {
    TAG("tag"),
    FANDOM("fandom"),
    RELATIONSHIP("relationship"),
    CHARACTER("character"),
    FREEFORM("freeform"),
    PSEUD("pseud")
}
