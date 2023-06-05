package com.edwnmrtnz.trendingrepo.core.domain

interface GithubRepoGateway {

    suspend fun load(): List<GithubRepo>

    suspend fun reload(): List<GithubRepo>
}
