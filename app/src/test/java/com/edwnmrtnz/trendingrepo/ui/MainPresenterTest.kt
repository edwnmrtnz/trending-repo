package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.GithubRepoTestData
import com.edwnmrtnz.trendingrepo.core.domain.FetchTrendingReposUseCase
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyHttpErrorException
import com.edwnmrtnz.trendingrepo.presenter.StatefulPresenter
import com.edwnmrtnz.trendingrepo.presenter.TestView
import com.github.amaterasu.localtest.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    internal class FakeView : MainScreenView, TestView<MainUiState>()
    private lateinit var presenter: MainPresenter
    private lateinit var view: FakeView

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var fetchTrendingGithubReposUseCase: FetchTrendingReposUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        StatefulPresenter.setScope(coroutineTestRule.scope)
        presenter = MainPresenter(fetchTrendingGithubReposUseCase)
        view = FakeView()
    }

    @Test
    fun `initial state must be loading`() = runBlocking {
        presenter.bind(view)

        Truth.assertThat(view.first().isLoading).isTrue()
    }

    @Test
    fun `when fetching repos done, flip loading state`() = runBlocking {
        Mockito.`when`(fetchTrendingGithubReposUseCase.execute(Unit))
            .thenReturn(listOf(SAMPLE_REPO))

        presenter.bind(view)

        Truth.assertThat(view.state().isLoading).isFalse()
    }

    @Test
    fun `when fetching failed, flip loading state and reflect error`() = runBlocking {
        Mockito.`when`(fetchTrendingGithubReposUseCase.execute(Unit))
            .then {
                throw TrendyHttpErrorException(404, "Not Found", Exception("Not Found"))
            }

        presenter.bind(view)

        Truth.assertThat(view.state().isLoading).isFalse()
        Truth.assertThat(view.state().loadError).isNotNull()
    }

    @Test
    fun `empty repos should still be considered a success`() = runBlocking {
        Mockito.`when`(fetchTrendingGithubReposUseCase.execute(Unit))
            .thenReturn(listOf())

        presenter.bind(view)

        Truth.assertThat(view.state().isLoading).isFalse()
        Truth.assertThat(view.state().loadError).isNull()
    }

    @Test
    fun `when fetch repos done, reflect repos to state`() = runBlocking {
        Mockito.`when`(fetchTrendingGithubReposUseCase.execute(Unit))
            .thenReturn(listOf(SAMPLE_REPO))

        presenter.bind(view)

        Truth.assertThat(view.state().repos).isNotEmpty()
    }

    @Test
    fun `when retry and got repos, reflect repos to state`() = runBlocking {
        Mockito.`when`(fetchTrendingGithubReposUseCase.execute(Unit))
            .then {
                throw TrendyHttpErrorException(404, "Oops", Exception("oops"))
            }.thenReturn(
                listOf(SAMPLE_REPO)
            )
        presenter.bind(view)

        presenter.onRetryAction()

        Truth.assertThat(view.state().repos).isNotEmpty()
        Truth.assertThat(view.state().loadError).isNull()
        Truth.assertThat(view.state().isLoading).isFalse()
    }

    companion object {
        private val SAMPLE_REPO = GithubRepoTestData.build()
    }
}
