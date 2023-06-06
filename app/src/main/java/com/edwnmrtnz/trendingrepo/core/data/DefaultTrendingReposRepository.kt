package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepo
import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepoGateway
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyHttpErrorException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyServiceFailureException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException

@Singleton
class DefaultTrendingReposRepository @Inject constructor(
    private val api: GithubAPI,
    private val dao: TrendingGithubRepoDao
) : TrendingRepoGateway {

    private val cache: MutableList<TrendingRepo> = mutableListOf()

    override suspend fun load(): List<TrendingRepo> {
        return cache.takeIf { it.isNotEmpty() } ?: try {
            val rows = dao.get()
            if (rows.isNotEmpty()) {
                cache.addAll(rows.map { it.toTrendingRepo() })
                return cache
            }
            api.get().items.map { it.toTrendingRepo() }.also {
                cache.clear()
                cache.addAll(it)
                dao.insert(it.map { row -> row.toDbRow() })
            }
        } catch (exception: HttpException) {
            throw TrendyHttpErrorException(
                exception = exception,
                code = exception.code(),
                message = exception.message()
            )
        } catch (exception: IOException) {
            throw TrendyServiceFailureException(
                message = exception.message ?: "Load failed",
                exception = exception
            )
        }
    }

    override suspend fun reload(): List<TrendingRepo> {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        cache.clear()
    }
}
