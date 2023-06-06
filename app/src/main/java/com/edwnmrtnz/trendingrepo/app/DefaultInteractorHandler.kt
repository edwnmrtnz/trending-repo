package com.edwnmrtnz.trendingrepo.app

import android.util.Log
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorExceptionHandler
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import java.lang.Exception
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultInteractorHandler : InteractorHandler {
    override fun getDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    // Can be use for analytics
    override fun getExceptionHandler(): InteractorExceptionHandler {
        return object : InteractorExceptionHandler {
            override suspend fun onError(callerName: String, exception: Exception): Exception {
                Log.e(TAG, "Error occur for $callerName")
                Log.e(TAG, exception.message ?: "")
                return exception
            }
        }
    }

    companion object {
        private const val TAG = "InteractorHandler"
    }
}
