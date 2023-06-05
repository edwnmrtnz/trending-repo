package com.edwnmrtnz.trendingrepo.core.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher

interface InteractorHandler {

    fun getDispatcher(): CoroutineDispatcher

    fun getExceptionHandler(): InteractorExceptionHandler
}
