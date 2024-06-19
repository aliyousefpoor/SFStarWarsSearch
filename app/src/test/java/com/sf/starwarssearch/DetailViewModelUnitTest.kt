package com.sf.starwarssearch

import app.cash.turbine.test
import com.sf.starwarssearch.domain.model.FilmsModel
import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.model.PlanetModel
import com.sf.starwarssearch.domain.model.SpeciesModel
import com.sf.starwarssearch.domain.usecase.GetPeopleDetailUseCase
import com.sf.starwarssearch.ui.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class DetailViewModelUnitTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var detailViewModel: DetailViewModel

    private val getPeopleDetailUseCase: GetPeopleDetailUseCase = mockk()


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        detailViewModel = DetailViewModel(getPeopleDetailUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `test getSearchResult success`() = runBlocking {
        coEvery {
            getPeopleDetailUseCase.invoke(listOf(), listOf(), "https://swapi.dev/api/planets/2/")
        } returns PeopleDetailModel(
            species = listOf(
                SpeciesModel(
                    name = "species",
                    language = "lng",
                    homeWorld = "https://swapi.dev/api/planets/2/"
                )
            ),
            planetPopulation = listOf(PlanetModel(population = "200")),
            films = listOf(FilmsModel(title = "film1", openingCrawl = "Detail film1"))
        )


        detailViewModel.getPeopleDetail(
            listOf(),
            listOf(),
            "https://swapi.dev/api/planets/2/"
        )


        detailViewModel.state.test {
            val item = awaitItem()
            assertEquals(item?.isLoading, false)
            assertEquals(item?.isError, false)
            assertEquals(item?.peopleDetailResults?.planetPopulation?.get(0)?.population, "200")
            assertEquals(item?.peopleDetailResults?.films?.size, 1)
            assertEquals(item?.peopleDetailResults?.species?.get(0)?.name, "species")
            assertEquals(item?.peopleDetailResults?.films?.get(0)?.title, "film1")
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `test getSearchResult failed`() = runTest {

        coEvery {
            getPeopleDetailUseCase.invoke(listOf(), listOf(), "https://swapi.dev/api/planets/2/")
        } throws IOException("Error Message")


        detailViewModel.getPeopleDetail(
            listOf(),
            listOf(),
            "https://swapi.dev/api/planets/2/"
        )


        detailViewModel.state.test {
            val item = awaitItem()
            assertEquals(item?.isLoading , false)
            assertEquals(item?.isError, true)
            assertEquals(item?.peopleDetailResults, null)
            cancelAndIgnoreRemainingEvents()
        }
    }


}