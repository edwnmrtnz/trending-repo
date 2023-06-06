package com.edwnmrtnz.trendingrepo.core.domain

data class TrendingRepo(
    val repoId: Int,
    val avatar: String,
    val username: String,
    val repoName: String,
    val description: String?,
    val primaryLanguage: String,
    val starCount: Int
) {
    fun hasPrimaryLanguage(): Boolean {
        return primaryLanguage.isNotEmpty()
    }

    fun hasDescription(): Boolean {
        return !description.isNullOrBlank()
    }
}
