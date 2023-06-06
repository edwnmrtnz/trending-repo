package com.edwnmrtnz.trendingrepo.core.domain

interface TrendingRepoGateway {

    suspend fun load(): List<TrendingRepo>

    suspend fun clear()
}
