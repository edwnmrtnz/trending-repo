package com.edwnmrtnz.trendingrepo.core.domain

import com.edwnmrtnz.trendingrepo.GithubRepoTestData
import com.edwnmrtnz.trendingrepo.TestInteractorHandler
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FetchTrendingGithubRepoUseCaseTest {

    private lateinit var sut: FetchTrendingGithubRepoUseCase

    @Mock
    private lateinit var gateway: GithubRepoGateway

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = FetchTrendingGithubRepoUseCase(
            TestInteractorHandler(),
            gateway
        )
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

    companion object {
        private val SAMPLE_REPO = GithubRepoTestData.build()
    }
}
