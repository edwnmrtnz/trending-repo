package com.edwnmrtnz.trendingrepo.core

import com.edwnmrtnz.trendingrepo.AppTestConstant
import com.edwnmrtnz.trendingrepo.TestAssetReader
import com.edwnmrtnz.trendingrepo.core.data.GithubAPI
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetTrendingReposApiTest {

    private val raw = TestAssetReader.read(AppTestConstant.MODULE, "trending.json")

    private lateinit var server: MockWebServer
    private lateinit var api: GithubAPI

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubAPI::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should be able to deserialize api response`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(raw)
        server.enqueue(response)

        val result = api.get()

        Truth.assertThat(result.items).isNotEmpty()
    }
}
