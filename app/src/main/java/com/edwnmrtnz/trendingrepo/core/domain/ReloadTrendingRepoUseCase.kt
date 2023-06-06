package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.core.domain.interactor.Interactor
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler

class ReloadTrendingRepoUseCase(
    interactorHandler: InteractorHandler,
    private val gateway: TrendingRepoGateway
) : Interactor<List<TrendingRepo>, Unit>(interactorHandler) {

    override suspend fun run(params: Unit): List<TrendingRepo> {
        return gateway.reload()
    }
}
