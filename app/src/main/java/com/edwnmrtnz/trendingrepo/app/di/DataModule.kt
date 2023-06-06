package com.edwnmrtnz.trendingrepo.app.di

import com.edwnmrtnz.trendingrepo.app.DefaultInteractorHandler
import com.edwnmrtnz.trendingrepo.app.TrendyApplication
import com.edwnmrtnz.trendingrepo.core.data.DefaultGithubReposRepository
import com.edwnmrtnz.trendingrepo.core.data.GithubAPI
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepoGateway
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun githubGateway(repo: DefaultGithubReposRepository): GithubRepoGateway

    @Binds
    @Singleton
    abstract fun interactorHandler(handler: DefaultInteractorHandler): InteractorHandler

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(TrendyApplication.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Reusable
        fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
            return retrofit.create(GithubAPI::class.java)
        }
    }
}
