package com.edwnmrtnz.trendingrepo.core.data

import retrofit2.http.GET

interface GithubAPI {

    @GET("search/repositories?q=language=+sort:stars")
    suspend fun get(): GithubRepoRawResponse
}
