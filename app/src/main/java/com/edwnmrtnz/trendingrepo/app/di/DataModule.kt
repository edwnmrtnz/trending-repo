package com.edwnmrtnz.trendingrepo.app.di

import com.edwnmrtnz.trendingrepo.app.DefaultInteractorHandler
import com.edwnmrtnz.trendingrepo.core.data.FakeGithubReposRepository
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepoGateway
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun githubGateway(repo: FakeGithubReposRepository): GithubRepoGateway

    @Binds
    @Singleton
    abstract fun interactorHandler(handler: DefaultInteractorHandler): InteractorHandler
}
