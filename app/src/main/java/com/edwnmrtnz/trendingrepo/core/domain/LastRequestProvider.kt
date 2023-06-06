package com.edwnmrtnz.trendingrepo.core.domain

interface LastRequestProvider {

    suspend fun isToday(): Boolean

    suspend fun update()
}
