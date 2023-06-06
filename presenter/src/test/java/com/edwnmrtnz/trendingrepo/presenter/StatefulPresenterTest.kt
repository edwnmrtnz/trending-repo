package com.edwnmrtnz.trendingrepo.presenter

import com.github.amaterasu.localtest.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatefulPresenterTest {

    data class FakeUiState(val isLoading: Boolean)
    class FakeView : TestView<FakeUiState>()
    class FakePresenter : StatefulPresenter<FakeUiState, FakeView>() {
        override fun initialState(): FakeUiState {
            return FakeUiState(isLoading = true)
        }

        override fun onCreated(state: FakeUiState) {
            super.onCreated(state)
            render { it.copy(isLoading = false) }
        }
    }

    private lateinit var presenter: FakePresenter
    private lateinit var view: FakeView

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        StatefulPresenter.setScope(coroutineTestRule.scope)
        presenter = FakePresenter()
        view = FakeView()
    }

    @Test
    fun `initial state must be loading`() = runBlocking {
        presenter.bind(view)

        Truth.assertThat(view.first().isLoading).isTrue()
    }

    @Test
    fun `after first bind, flip loading`() = runBlocking {
        presenter.bind(view)

        Truth.assertThat(view.state().isLoading).isFalse()
    }
}
