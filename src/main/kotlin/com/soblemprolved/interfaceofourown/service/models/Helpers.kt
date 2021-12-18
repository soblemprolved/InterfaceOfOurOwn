package com.soblemprolved.interfaceofourown.service.models

enum class AutocompleteType(
    val pathSegment: String
) {
    TAG("tag"),
    FANDOM("fandom"),
    RELATIONSHIP("relationship"),
    CHARACTER("character"),
    FREEFORM("freeform"),
    PSEUD("pseud");
}

@JvmInline
value class Tag(val tag: String) {
    val urlEncodedTag: String
        get() = encodeTag(tag)

    private fun encodeTag(tag: String): String {
        return tag.replace("/", "*s*")
            .replace("&", "*a*")
            .replace(".", "*d*")
            .replace("?", "*q*")
            .replace("#", "*h*")
    }
}
