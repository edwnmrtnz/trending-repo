package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.core.domain.interactor.Interactor
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler

class ReloadTrendingGithubReposUseCase(
    interactorHandler: InteractorHandler,
    private val gateway: GithubRepoGateway
) : Interactor<List<GithubRepo>, Unit>(interactorHandler) {

    override suspend fun run(params: Unit): List<GithubRepo> {
        return gateway.reload()
    }
}
