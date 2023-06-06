package com.edwnmrtnz.trendingrepo.presenter

interface ScreenView<State> {
    fun render(state: State)
}
