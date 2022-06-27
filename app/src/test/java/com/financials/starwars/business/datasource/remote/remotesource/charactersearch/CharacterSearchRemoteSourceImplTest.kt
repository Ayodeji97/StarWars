package com.financials.starwars.business.datasource.remote.remotesource.charactersearch

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.remotesource.model.CharacterResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CharacterSearchRemoteSourceImplTest {

    private lateinit var mockWebServer : MockWebServer
    private lateinit var service : StarWarsService
    private lateinit var characterSearchRemoteSource: CharacterSearchRemoteSource
    private lateinit var baseUrl: HttpUrl

    @Before
    fun setup () {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("")
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(StarWarsService::class.java)

    }

    @Test
    fun see () = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(CharacterResponse.success_response)
        )
    }

}