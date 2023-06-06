package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.core.domain.interactor.Interactor
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import javax.inject.Inject

class FetchTrendingGithubReposUseCase @Inject constructor(
    interactorHandler: InteractorHandler,
    private val gateway: GithubRepoGateway,
    private val lastRequestProvider: LastRequestProvider
) : Interactor<List<GithubRepo>, Unit>(interactorHandler) {

    override suspend fun run(params: Unit): List<GithubRepo> {
        return if (lastRequestProvider.isToday()) {
            gateway.load()
        } else {
            gateway.clear()
            gateway.load()
        }
    }
}
