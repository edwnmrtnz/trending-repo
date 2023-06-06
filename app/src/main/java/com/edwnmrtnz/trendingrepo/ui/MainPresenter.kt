package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.StatefulPresenter
import com.edwnmrtnz.trendingrepo.core.domain.FetchTrendingGithubReposUseCase
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyException
import kotlinx.coroutines.launch

class MainPresenter(
    private val fetchTrendingGithubReposUseCase: FetchTrendingGithubReposUseCase
) : StatefulPresenter<MainUiState, MainScreenView>() {

    override fun initialState() = MainUiState(isLoading = true)

    override fun onCreated(state: MainUiState) {
        super.onCreated(state)

        scope.launch {
            try {
                fetchTrendingGithubReposUseCase.execute(Unit)
                render { it.copy(isLoading = false) }
            } catch (exception: TrendyException) {
                render {
                    it.copy(isLoading = false, loadError = exception.message ?: DEFAULT_ERROR)
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_ERROR = "Oops... Unknown error occur"
    }
}
