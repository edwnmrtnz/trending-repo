package com.edwnmrtnz.trendingrepo.core.domain.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

abstract class Interactor<out Response, in Params> (
    private val handler: InteractorHandler
) {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(handler.getDispatcher() + job)

    suspend fun execute(params: Params): Response = withContext(
        scope.coroutineContext + handler.getDispatcher()
    ) {
        try {
            run(params)
        } catch (exception: Exception) {
            throw handler.getExceptionHandler().onError(getSubclassName(), exception)
        }
    }

    abstract suspend fun run(params: Params): Response

    private fun getSubclassName(): String {
        return this.javaClass.asSubclass(this.javaClass).simpleName
    }
}
