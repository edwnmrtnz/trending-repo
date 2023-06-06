package com.edwnmrtnz.trendingrepo.core.data

import com.edwnmrtnz.trendingrepo.AppTestConstant
import com.edwnmrtnz.trendingrepo.TestAssetReader
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyHttpErrorException
import com.edwnmrtnz.trendingrepo.core.domain.exceptions.TrendyServiceFailureException
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.ByteString
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
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(TRENDING)
        server.enqueue(response)

        val result = sut.load()

        Truth.assertThat(result).isNotEmpty()
    }

    @Test(expected = TrendyHttpErrorException::class)
    fun `should be able to throw trendy http exception when api failed on load`() = runTest {
        val response = MockResponse()
            .setResponseCode(404)
            .setBody("{}")
        server.enqueue(response)

        sut.load()
    }

    @Test(expected = TrendyServiceFailureException::class)
    fun `should be able to throw trendy service exception when no network on load`() = runTest {
        val response = MockResponse()
            .setBody(okio.Buffer().write(byteString = ByteString.EMPTY))
            .setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY)
        server.enqueue(response)

        sut.load()
    }

    @Test
    fun `should not re-fetch when in memory cache already available`() = runTest {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(TRENDING)
        server.enqueue(response)
        sut.load()
        Truth.assertThat(server.requestCount).isEqualTo(1)

        sut.load()
        Truth.assertThat(server.requestCount).isEqualTo(1)
    }

    companion object {
        private val TRENDING = TestAssetReader.read(AppTestConstant.MODULE, "trending.json")
    }
}
