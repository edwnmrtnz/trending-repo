package com.edwnmrtnz.trendingrepo.core.data

import android.content.Context
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepoGateway
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FakeGithubReposRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : GithubRepoGateway {

    private var exception: Exception? = null

    private fun read(fileNameWithExtension: String): String {
        return context
            .assets
            .open(fileNameWithExtension)
            .bufferedReader()
            .use { it.readText() }
    }

    override suspend fun load(): List<GithubRepo> {
        throwIfSet()
        val raw = read("trending.json")
        return Gson().fromJson(raw, GithubRepoRawResponse::class.java)
            .items.map { it.toDomain() }
    }

    override suspend fun reload(): List<GithubRepo> {
        throwIfSet()
        return load()
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
