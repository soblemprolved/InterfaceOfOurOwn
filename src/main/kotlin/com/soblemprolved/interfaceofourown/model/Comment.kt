package com.soblemprolved.interfaceofourown.model

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val author: UserName,
    val chapter: Int,
    val postedDateTime: LocalDateTime,
    val body: Html,
    val commentDepth: Int,   // should i use depth or use parent id as gauge?
    val hasHiddenChildren: Boolean = false,
    val numHiddenChildren: Int = 0
)
