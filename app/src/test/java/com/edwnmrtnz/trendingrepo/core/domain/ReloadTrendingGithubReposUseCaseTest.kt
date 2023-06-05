package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.GithubRepoTestData
import com.edwnmrtnz.trendingrepo.TestInteractorHandler
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyServiceFailureException
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ReloadTrendingGithubReposUseCaseTest {

    @Mock
    private lateinit var gateway: GithubRepoGateway

    private lateinit var sut: ReloadTrendingGithubReposUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = ReloadTrendingGithubReposUseCase(
            interactorHandler = TestInteractorHandler(),
            gateway = gateway
        )
    }

    @Test
    fun `should return new data when reload`() = runTest {
        Mockito.`when`(gateway.reload()).thenReturn(listOf(SAMPLE_REPO))

        val result = sut.execute(Unit)

        Truth.assertThat(result).isNotEmpty()
    }

    @Test(expected = TrendyException::class)
    fun `should be able to handle exceptions`() = runTest {
        Mockito.`when`(gateway.reload()).then {
            throw TrendyServiceFailureException("Network Error", exception = IOException("Network"))
        }

        sut.execute(Unit)
    }

    companion object {
        private val SAMPLE_REPO = GithubRepoTestData.build()
    }
}
