package com.edwnmrtnz.trendingrepo.core.data

import android.text.format.DateUtils
import com.edwnmrtnz.trendingrepo.core.domain.LastRequestProvider
import javax.inject.Inject

class DefaultLastRequestProvider @Inject constructor(
    private val dao: LastRequestDao
) : LastRequestProvider {

    override suspend fun isToday(): Boolean {
        val lastRequest = dao.get()?.toDate()
        return if (lastRequest == null) false else DateUtils.isToday(lastRequest.time)
    }

    override suspend fun update() {
        dao.save(LastRequestRow.create())
    }
}
