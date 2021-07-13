package service

enum class AutocompleteType(
    val value: String
) {
    TAG("tag"),
    CHARACTER("character"),
    FANDOM("fandom"),
    FREEFORM("freeform"),
    RELATIONSHIP("relationship"),
    PSEUD("pseud")
}
