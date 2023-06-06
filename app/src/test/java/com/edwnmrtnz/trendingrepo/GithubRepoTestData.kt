package com.edwnmrtnz.trendingrepo

import com.edwnmrtnz.trendingrepo.core.data.GithubRepoRawResponse
import com.edwnmrtnz.trendingrepo.core.data.toTrendingRepo
import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepo
import com.google.gson.Gson

object GithubRepoTestData {

    fun build(): TrendingRepo {
        val raw = TestAssetReader.read(AppTestConstant.MODULE, "trending.json")
        return Gson().fromJson(raw, GithubRepoRawResponse::class.java)
            .items.first().toTrendingRepo()
    }
}
