package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.core.domain.interactor.Interactor
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import javax.inject.Inject

class FetchTrendingReposUseCase @Inject constructor(
    interactorHandler: InteractorHandler,
    private val gateway: TrendingRepoGateway,
    private val lastRequestProvider: LastRequestProvider
) : Interactor<List<TrendingRepo>, Unit>(interactorHandler) {

    override suspend fun run(params: Unit): List<TrendingRepo> {
        return if (lastRequestProvider.isToday()) {
            gateway.load()
        } else {
            gateway.clear()
            gateway.load().also {
                lastRequestProvider.update()
            }
        }
    }
}
