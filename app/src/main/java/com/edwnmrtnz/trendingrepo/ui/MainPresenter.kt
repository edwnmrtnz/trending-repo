package com.edwnmrtnz.trendingrepo.ui

import com.edwnmrtnz.trendingrepo.StatefulPresenter

class MainPresenter : StatefulPresenter<MainUiState, MainScreenView>() {

    override fun initialState() = MainUiState(isLoading = true)
}
