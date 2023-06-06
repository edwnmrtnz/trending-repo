package com.edwnmrtnz.trendingrepo.core

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.edwnmrtnz.trendingrepo.GithubRepoTestData
import com.edwnmrtnz.trendingrepo.core.data.TrendingGithubRepoDao
import com.edwnmrtnz.trendingrepo.core.data.TrendingRepoDatabase
import com.edwnmrtnz.trendingrepo.core.data.toDbRow
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Config.OLDEST_SDK], application = Application::class)
class TrendingGithubRepoDaoTest {

    private lateinit var sut: TrendingGithubRepoDao

    @Before
    fun setup() {
        sut = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TrendingRepoDatabase::class.java
        ).allowMainThreadQueries()
            .build()
            .trendingGithubRepoDao()
    }

    @Test
    fun `should be able to save trending repos`() = runBlocking {
        val repo = GithubRepoTestData.build()

        sut.insert(listOf(repo.toDbRow()))

        Truth.assertThat(sut.get()).isNotEmpty()
    }

    @Test
    fun `should be able to clear trending repos`() = runBlocking {
        val repo = GithubRepoTestData.build().toDbRow()
        sut.insert(listOf(repo))
        Truth.assertThat(sut.get()).isNotEmpty()

        sut.clear()
        Truth.assertThat(sut.get()).isEmpty()
    }
}
