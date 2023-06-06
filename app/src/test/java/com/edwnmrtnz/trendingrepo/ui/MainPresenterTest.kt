package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.StatefulPresenter
import com.edwnmrtnz.trendingrepo.TestView
import com.github.amaterasu.localtest.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainPresenterTest {

    internal class FakeView : MainScreenView, TestView<MainUiState>()
    private lateinit var presenter: MainPresenter
    private lateinit var view: FakeView

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        StatefulPresenter.setScope(coroutineTestRule.scope)
        presenter = MainPresenter()
        view = FakeView()
    }

    @Test
    fun `initial state must be loading`() = runBlocking {
        presenter.bind(view)

        Truth.assertThat(view.first().isLoading).isTrue()
    }
}
