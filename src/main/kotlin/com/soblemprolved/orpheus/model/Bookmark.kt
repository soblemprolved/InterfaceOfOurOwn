package com.soblemprolved.orpheus.model

import java.time.LocalDate

data class Bookmark(
    val userName: UserName,
    val tags: List<String>,
    val collections: List<CollectionName>,
    val date: LocalDate,
    val notes: Html,
    val bookmarkType: BookmarkType
) {

}
