package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.AppTestConstant
import com.edwnmrtnz.trendingrepo.TestAssetReader
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultGithubReposRepositoryTest {

    private lateinit var sut: DefaultGithubReposRepository
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        sut = DefaultGithubReposRepository(
            Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAPI::class.java)
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should be able to return github repos on load`() = runTest {
        val raw = TestAssetReader.read(AppTestConstant.MODULE, "trending.json")
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(raw)
        server.enqueue(response)

        val result = sut.load()

        Truth.assertThat(result).isNotEmpty()
    }
}
