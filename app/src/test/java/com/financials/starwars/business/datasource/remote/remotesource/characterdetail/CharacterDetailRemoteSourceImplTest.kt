package com.financials.starwars.business.datasource.remote.remotesource.characterdetail

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.utils.MainCoroutineRule
import com.financials.starwars.business.datasource.utils.TestConstants
import com.financials.starwars.business.datasource.utils.TestConstants.CHARACTER_JSON_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.CHARACTER_URL
import com.financials.starwars.business.datasource.utils.TestConstants.FILM_JSON_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.FILM_TEST_TILE
import com.financials.starwars.business.datasource.utils.TestConstants.FILM_URL
import com.financials.starwars.business.datasource.utils.TestConstants.PLANET_JSON_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.PLANET_TEST_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.PLANET_TEST_POPULATION
import com.financials.starwars.business.datasource.utils.TestConstants.PLANET_URL
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepositoryImpl
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDetailDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.FilmDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.PlanetDtoMapper
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterDetailRemoteSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: StarWarsService
    private lateinit var characterDetailRemoteSource: CharacterDetailRemoteSource
    private lateinit var characterDetailRepository: CharacterDetailRepository
    private lateinit var baseUrl: HttpUrl
    private val characterDetailDtoMapper: CharacterDetailDtoMapper = CharacterDetailDtoMapper()
    private val planetDtoMapper: PlanetDtoMapper = PlanetDtoMapper()
    private val filmDtoMapper: FilmDtoMapper = FilmDtoMapper()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("")
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(StarWarsService::class.java)

        characterDetailRemoteSource = CharacterDetailRemoteSourceImpl(service, Dispatchers.Main)
        characterDetailRepository =
            CharacterDetailRepositoryImpl(
                characterDetailRemoteSource,
                characterDetailDtoMapper, planetDtoMapper, filmDtoMapper
            )
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `check that getCharacter call return the correct data` () = runBlocking {
        enqueueMockResponse(CHARACTER_JSON_NAME)
        val apiResponseBody = service.getCharacterDetail(CHARACTER_URL).body()
        assertThat(apiResponseBody).isNotNull()
        assertThat(apiResponseBody?.films).isNotEmpty()
        assertThat(apiResponseBody?.species).isNotEmpty()
        assertThat(apiResponseBody?.url).isNotEmpty()
    }

    @Test
    fun `check that getPlant call return the correct data` () = runBlocking{
        enqueueMockResponse(PLANET_JSON_NAME)
        val apiResponseBody = service.getPlanetDetail(PLANET_URL).body()
        assertThat(apiResponseBody).isNotNull()
        assertThat(apiResponseBody?.name).isEqualTo(PLANET_TEST_NAME)
        assertThat(apiResponseBody?.population).isEqualTo(PLANET_TEST_POPULATION)
    }

    @Test
    fun `check that getFilms call return the correct data` () = runBlocking{
        enqueueMockResponse(FILM_JSON_NAME)
        val apiResponseBody = service.getFilmDetail(FILM_URL)
        assertThat(apiResponseBody).isNotNull()
        assertThat(apiResponseBody.title).isEqualTo(FILM_TEST_TILE)
    }
}