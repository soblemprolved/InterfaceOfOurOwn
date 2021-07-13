package model

enum class Rating(val code: Int) {
    NONE(9),
    GENERAL(10),
    TEEN(11),
    MATURE(12),
    EXPLICIT(13)
}

enum class Category(val code: Int) {
    GEN(21),
    FEMALE_MALE(22),
    MALE_MALE(23),
    OTHER(24),
    FEMALE_FEMALE(116),
    MULTI(2246)
}

enum class Warning(val code: Int) {
    CREATOR_CHOSE_NOT_TO_USE_WARNINGS(14),
    NO_WARNINGS(16),
    GRAPHIC_VIOLENCE(17),
    MAJOR_CHARACTER_DEATH(18),
    RAPE(19),
    UNDERAGE(20)
}

enum class SortOrder(val code: String) {
    AUTHOR("authors_to_sort_on"),
    TITLE("title_to_sort_on"),
    DATE_POSTED("created_at"),
    DATE_UPDATED("revised_at"),
    WORD_COUNT("word_count"),
    HITS("hits"),
    KUDOS("kudos_count"),
    COMMENTS("comments_count"),
    BOOKMARKS("bookmarks_count")
}
