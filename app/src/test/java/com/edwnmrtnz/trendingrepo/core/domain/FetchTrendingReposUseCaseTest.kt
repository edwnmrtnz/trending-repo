package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.GithubRepoTestData
import com.edwnmrtnz.trendingrepo.TestInteractorHandler
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyDomainException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyHttpErrorException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyServiceFailureException
import com.google.common.truth.Truth
import java.lang.IllegalStateException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FetchTrendingReposUseCaseTest {

    private lateinit var sut: FetchTrendingReposUseCase

    @Mock
    private lateinit var gateway: TrendingRepoGateway

    @Mock
    private lateinit var lastRequestProvider: LastRequestProvider

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = FetchTrendingReposUseCase(
            TestInteractorHandler(),
            gateway,
            lastRequestProvider
        )
        defaults()
    }

    private fun defaults() = runBlocking {
        Mockito.`when`(lastRequestProvider.isToday()).thenReturn(true)
    }

    @Test
    fun `should return list of github repo when no errors`() = runTest {
        Mockito.`when`(gateway.load()).thenReturn(listOf(SAMPLE_REPO))

        val repos = sut.execute(Unit)

        Truth.assertThat(repos).isNotEmpty()
    }

    @Test
    fun `should still empty list when repo returns empty`() = runTest {
        Mockito.`when`(gateway.load()).thenReturn(listOf())

        val repos = sut.execute(Unit)

        Truth.assertThat(repos).isEmpty()
    }

    @Test
    fun `should be able to handle trendy based exception`() = runTest {
        Mockito.`when`(gateway.load())
            .then { throw TrendyDomainException("Invalid Parameter") }
            .then {
                throw TrendyHttpErrorException(404, "Page not found", Exception("Page not found"))
            }.then {
                throw TrendyServiceFailureException("Network error", IOException("Network error"))
            }

        val count = 3
        var total = 0
        repeat(count) {
            try {
                sut.execute(Unit)
            } catch (exception: TrendyException) {
                total++
            }
        }

        Truth.assertThat(total).isEqualTo(count)
    }

    @Test(expected = IllegalStateException::class)
    fun `throwing non trendy based exception should just crash the app`() = runTest {
        Mockito.`when`(gateway.load()).then {
            throw IllegalStateException("Something went wrong")
        }

        sut.execute(Unit)
    }

    @Test
    fun `should fetch when last fetch is not today`() = runTest {
        Mockito.`when`(lastRequestProvider.isToday()).thenReturn(false)

        sut.execute(Unit)

        Mockito.verify(gateway).clear()
        Mockito.verify(gateway).load()
        Mockito.verify(lastRequestProvider).update()
    }

    companion object {
        private val SAMPLE_REPO = GithubRepoTestData.build()
    }
}
