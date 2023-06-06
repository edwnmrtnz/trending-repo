package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepo

fun GithubRepoRawResponse.Item.toTrendingRepo(): TrendingRepo {
    return TrendingRepo(
        repoId = this.id,
        avatar = this.owner.avatarUrl,
        username = this.owner.login,
        repoName = this.fullName,
        description = this.description,
        primaryLanguage = this.language ?: "",
        starCount = this.stargazersCount
    )
}

fun TrendingRepo.toDbRow(): TrendingGithubRepoRow {
    return TrendingGithubRepoRow(
        repoId = repoId,
        avatar = avatar,
        username = username,
        repoName = repoName,
        description = description,
        primaryLanguage = primaryLanguage,
        starCount = starCount
    )
}
