package com.edwnmrtnz.trendingrepo.core.domain.interactor

import java.lang.Exception

interface InteractorExceptionHandler {
    suspend fun onError(callerName: String, exception: Exception): Exception
}
