package com.financials.starwars.business.datasource.remote.remotesource.charactersearch

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.datasource.utils.MainCoroutineRule
import com.financials.starwars.business.datasource.utils.TestConstants.CHARACTERS_LIST_JSON_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.CHARACTER_JSON_NAME
import com.financials.starwars.business.datasource.utils.TestConstants.EMPTY_SEARCH_NUMBER
import com.financials.starwars.business.datasource.utils.TestConstants.REQUEST_PATH
import com.financials.starwars.business.datasource.utils.TestConstants.SEARCH_QUERY
import com.financials.starwars.business.datasource.utils.TestConstants.SEARCH_REQUEST_PATH
import com.financials.starwars.business.datasource.utils.TestConstants.SEARCH_SIZE
import com.financials.starwars.business.datasource.utils.TestConstants.SINGLE_SEARCH_QUERY
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepositoryImpl
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDtoMapper
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
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CharacterSearchRemoteSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: StarWarsService
    private lateinit var characterSearchRemoteSource: CharacterSearchRemoteSource
    private lateinit var charactersSearchRepository: CharactersSearchRepository
    private val characterDtoMapper: CharacterDtoMapper = CharacterDtoMapper()
    private lateinit var baseUrl: HttpUrl

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

        characterSearchRemoteSource = CharacterSearchRemoteSourceImpl(service, Dispatchers.Main)
        charactersSearchRepository =
            CharactersSearchRepositoryImpl(characterSearchRemoteSource, characterDtoMapper)

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
    fun `check that searchCharacters call makes request to the right path`() = runBlocking {
        enqueueMockResponse(CHARACTER_JSON_NAME)
        val apiResponseBody = service.searchCharacters(SEARCH_QUERY).body()
        val request = mockWebServer.takeRequest()
//        assertThat(apiResponseBody).isNotNull()
        assertThat(request.path).isEqualTo("$SEARCH_REQUEST_PATH=$SEARCH_QUERY")
    }

    @Test
    fun `check that searchCharacters return at least a corresponding correct data`(): Unit = runBlocking {
        enqueueMockResponse(CHARACTERS_LIST_JSON_NAME)
        val characters: List<CharacterDto>? =
            service.searchCharacters(SINGLE_SEARCH_QUERY).body()?.results

        val mappedResponse: List<Character> =
            characterDtoMapper.transformToDomain(characters ?: emptyList())
        assertThat(mappedResponse.first().name).contains(characters?.first()?.name)
    }

    @Test
    fun `check that searchCharacters returns correct list size`(): Unit = runBlocking {
        enqueueMockResponse(CHARACTERS_LIST_JSON_NAME)
        val characters: Result<CharacterSearchDto> =
            characterSearchRemoteSource.charactersSearch(SINGLE_SEARCH_QUERY)

        when (characters) {
            is Result.Error -> {
                assertThat(characters.exception).hasMessageThat()
            }
            is Result.Success -> {
                assertThat(characters.data?.results?.size).isNotEqualTo(EMPTY_SEARCH_NUMBER)
                assertThat(characters.data?.results?.size).isEqualTo(SEARCH_SIZE)
            }
            else -> {
            }
        }
    }

    @Test
    fun `check that searchCharacters return empty list when no character is found`() = runBlocking {
        val characters: Result<CharacterSearchDto> =
            characterSearchRemoteSource.charactersSearch(SEARCH_QUERY)

        when (characters) {
            is Result.Success -> {
                assertThat(characters.data?.results?.size).isEqualTo(EMPTY_SEARCH_NUMBER)
            }
        }
    }

    @Test
    fun `check that searchCharacters make a GET request`() = runBlocking {
        characterSearchRemoteSource.charactersSearch(SEARCH_QUERY)

        assertThat("GET $REQUEST_PATH?search=$SEARCH_QUERY HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

}