package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.core.domain.FetchTrendingReposUseCase
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyException
import com.edwnmrtnz.trendingrepo.presenter.StatefulPresenter
import javax.inject.Inject
import kotlinx.coroutines.launch

class MainPresenter @Inject constructor(
    private val fetchTrendingGithubReposUseCase: FetchTrendingReposUseCase
) : StatefulPresenter<MainUiState, MainScreenView>() {

    override fun initialState() = MainUiState(isLoading = true)

    override fun onCreated(state: MainUiState) {
        super.onCreated(state)

        loadRepos()
    }

    private fun loadRepos() {
        scope.launch {
            try {
                val result = fetchTrendingGithubReposUseCase.execute(Unit)
                render { it.copy(isLoading = false, repos = result, loadError = null) }
            } catch (exception: TrendyException) {
                render {
                    it.copy(isLoading = false, loadError = exception.message ?: DEFAULT_ERROR)
                }
            }
        }
    }

    fun onRetryAction() {
        render { it.copy(isLoading = true) }
        loadRepos()
    }

    companion object {
        private const val DEFAULT_ERROR = "Oops... Unknown error occur"
    }
}
