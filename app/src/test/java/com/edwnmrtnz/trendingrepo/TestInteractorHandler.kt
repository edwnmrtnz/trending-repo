package com.edwnmrtnz.trendingrepo

import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorExceptionHandler
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import java.lang.Exception
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestInteractorHandler(
    private val isPrintCallerNameAndException: Boolean = false
) : InteractorHandler {

    override fun getDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    override fun getExceptionHandler(): InteractorExceptionHandler {
        return object : InteractorExceptionHandler {
            override suspend fun onError(callerName: String, exception: Exception): Exception {
                if (isPrintCallerNameAndException) {
                    println("Exception occur for interactor: $callerName")
                    println(exception)
                }
                return exception
            }
        }
    }
}
