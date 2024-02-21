package com.soblemprolved.interfaceofourown.features.works

import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.Work

data class WorkPage(
    val work: Work,
    val csrfToken: Csrf
)
