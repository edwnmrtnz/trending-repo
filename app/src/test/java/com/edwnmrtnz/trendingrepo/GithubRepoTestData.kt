package com.edwnmrtnz.trendingrepo

import com.edwnmrtnz.trendingrepo.core.data.GithubRepoRawResponse
import com.edwnmrtnz.trendingrepo.core.data.toDomain
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo
import com.google.gson.Gson

object GithubRepoTestData {

    fun build(): GithubRepo {
        val raw = TestAssetReader.read(AppTestConstant.MODULE, "trending.json")
        return Gson().fromJson(raw, GithubRepoRawResponse::class.java).items.first().toDomain()
    }
}
