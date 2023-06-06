package com.edwnmrtnz.trendingrepo.core.data

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.edwnmrtnz.trendingrepo.core.domain.LastRequestProvider
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Config.OLDEST_SDK], application = Application::class)
class DefaultLastRequestProviderTest {

    private lateinit var db: TrendingRepoDatabase

    private lateinit var sut: LastRequestProvider

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TrendingRepoDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = DefaultLastRequestProvider(db.lastRequestDao())
    }

    @Test
    fun `should be able to save last request`() = runTest {
        sut.update()

        Truth.assertThat(db.lastRequestDao().get()).isNotNull()
    }
}
