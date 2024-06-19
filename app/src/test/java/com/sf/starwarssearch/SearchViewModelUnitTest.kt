package com.sf.starwarssearch

import app.cash.turbine.test
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.usecase.GetSearchResultUseCase
import com.sf.starwarssearch.ui.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class SearchViewModelUnitTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var searchViewModel: SearchViewModel

    private val getSearchResultUseCase: GetSearchResultUseCase = mockk()

    private val people = PeopleItemModel(
        name = "Leia Organa",
        height = "183 cm",
        mass = "",
        hairColor = "black",
        skinColor = "white",
        eyeColor = "brown",
        birthYear = "1996",
        gender = "183 cm",
        homeWorld = "183 cm",
        films = listOf(""),
        species = listOf(""),
        vehicles = listOf(""),
        starships = listOf(""),
        created = "",
        edited = "",
        url = ""
    )


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        searchViewModel = SearchViewModel(getSearchResultUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `test getSearchResult success`() = runBlocking {
        coEvery {
            getSearchResultUseCase.invoke("Leia", any())
        } returns SearchResponseModel(2, null, null, listOf(people))




        searchViewModel.getSearchResult("Leia")


        searchViewModel.state.test {
            val item = awaitItem()
            assertEquals(item.isLoading, false)
            assertEquals(item.isError, false)
            assertEquals(item.query, "Leia")
            assertEquals(item.searchResults?.next, null)
            assertEquals(item.searchResults?.previous, null)
            assertEquals(item.searchResults?.count, 2)
            assertEquals(item.searchResults?.results?.first()?.name, "Leia Organa")
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `test getSearchResult no result`() = runBlocking {


        coEvery {
            getSearchResultUseCase.invoke("Allla", any())
        } returns SearchResponseModel(0, null, null, listOf())


        searchViewModel.getSearchResult("Allla")


        searchViewModel.state.test {
            val item = awaitItem()
            assertEquals(item.isLoading, false)
            assertEquals(item.isError, false)
            assertEquals(item.query, "Allla")
            assertEquals(item.searchResults?.next, null)
            assertEquals(item.searchResults?.previous, null)
            assertEquals(item.searchResults?.count, 0)
            assertEquals(item.searchResults?.results?.isEmpty(), true)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `test getSearchResult failed`() = runBlocking {


        coEvery {
            getSearchResultUseCase.invoke("ali", any())
        } throws IOException("Error Message")


        searchViewModel.getSearchResult("ali")


        searchViewModel.state.test {
            val item = awaitItem()
            assertEquals(item.query, "ali")
            assertEquals(item.searchResults, null)
            cancelAndIgnoreRemainingEvents()
        }
    }


}