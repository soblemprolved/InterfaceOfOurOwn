package com.soblemprolved.interfaceofourown.model.pages

import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.Work

data class WorkPage(
    val work: Work,
    val csrfToken: Csrf
)
