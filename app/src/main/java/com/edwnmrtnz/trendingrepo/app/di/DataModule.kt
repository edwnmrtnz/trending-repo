package com.edwnmrtnz.trendingrepo.app.di

import android.content.Context
import com.edwnmrtnz.trendingrepo.app.DefaultInteractorHandler
import com.edwnmrtnz.trendingrepo.app.TrendyApplication
import com.edwnmrtnz.trendingrepo.core.data.DefaultLastRequestProvider
import com.edwnmrtnz.trendingrepo.core.data.DefaultTrendingReposRepository
import com.edwnmrtnz.trendingrepo.core.data.GithubAPI
import com.edwnmrtnz.trendingrepo.core.data.LastRequestDao
import com.edwnmrtnz.trendingrepo.core.data.TrendingGithubRepoDao
import com.edwnmrtnz.trendingrepo.core.data.TrendingRepoDatabase
import com.edwnmrtnz.trendingrepo.core.domain.LastRequestProvider
import com.edwnmrtnz.trendingrepo.core.domain.TrendingRepoGateway
import com.edwnmrtnz.trendingrepo.core.domain.interactor.InteractorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun githubGateway(repo: DefaultTrendingReposRepository): TrendingRepoGateway

    @Binds
    @Singleton
    abstract fun interactorHandler(handler: DefaultInteractorHandler): InteractorHandler

    @Binds
    abstract fun lastRequestProvider(provider: DefaultLastRequestProvider): LastRequestProvider

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(TrendyApplication.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): TrendingRepoDatabase {
            return TrendingRepoDatabase.getInstance(context)
        }

        @Provides
        fun provideLastRequestDao(database: TrendingRepoDatabase): LastRequestDao {
            return database.lastRequestDao()
        }

        @Provides
        fun provideTrendingGithubRepoDao(database: TrendingRepoDatabase): TrendingGithubRepoDao {
            return database.trendingGithubRepoDao()
        }

        @Provides
        @Reusable
        fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
            return retrofit.create(GithubAPI::class.java)
        }
    }
}
