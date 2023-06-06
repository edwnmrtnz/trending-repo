package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo

data class MainUiState(
    val isLoading: Boolean = false,
    val loadError: String? = null,
    val repos: List<GithubRepo> = mutableListOf()
)
