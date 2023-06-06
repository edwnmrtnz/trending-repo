package com.edwnmrtnz.trendingrepo.core.data

import android.text.format.DateUtils
import com.edwnmrtnz.trendingrepo.core.domain.LastRequestProvider
import java.util.Date
import javax.inject.Inject

class DefaultLastRequestProvider @Inject constructor() : LastRequestProvider {

    private var lastRequest = Date()

    override suspend fun isToday(): Boolean {
        return DateUtils.isToday(lastRequest.time)
    }

    override suspend fun update() {
        lastRequest = Date()
    }
}
