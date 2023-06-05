package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo

fun GithubRepoRawResponse.Item.toDomain(): GithubRepo {
    return GithubRepo(
        repoId = this.id,
        avatar = this.owner.avatarUrl,
        username = this.owner.login,
        repoName = this.fullName,
        description = this.description,
        primaryLanguage = this.language ?: "",
        starCount = this.stargazersCount
    )
}
