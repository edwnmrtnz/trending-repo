package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepoGateway
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyHttpErrorException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyServiceFailureException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException

@Singleton
class DefaultGithubReposRepository @Inject constructor(
    private val api: GithubAPI
) : GithubRepoGateway {

    override suspend fun load(): List<GithubRepo> {
        return try {
            api.get().items.map { it.toDomain() }
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

    override suspend fun reload(): List<GithubRepo> {
        TODO("Not yet implemented")
    }
}
