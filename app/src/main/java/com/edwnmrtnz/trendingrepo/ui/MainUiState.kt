package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepo

data class MainUiState(
    val isLoading: Boolean = false,
    val loadError: String? = null,
    val repos: List<TrendingRepo> = mutableListOf()
)
