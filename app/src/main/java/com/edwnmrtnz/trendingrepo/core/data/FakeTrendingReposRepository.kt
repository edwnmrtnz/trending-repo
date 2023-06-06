package com.edwnmrtnz.trendingrepo.core.data

import android.content.Context
import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepo
import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepoGateway
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FakeTrendingReposRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : TrendingRepoGateway {

    private var exception: Exception? = null

    private fun read(fileNameWithExtension: String): String {
        return context
            .assets
            .open(fileNameWithExtension)
            .bufferedReader()
            .use { it.readText() }
    }

    override suspend fun load(): List<TrendingRepo> {
        throwIfSet()
        val raw = read("trending.json")
        return Gson().fromJson(raw, GithubRepoRawResponse::class.java)
            .items.map { it.toTrendingRepo() }
    }

    override suspend fun reload(): List<TrendingRepo> {
        throwIfSet()
        return load()
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

    private fun throwIfSet() {
        exception?.let { exception -> throw exception }
    }

    fun fail(exception: Exception) {
        this.exception = exception
    }

    fun clean() {
        exception = null
    }
}
