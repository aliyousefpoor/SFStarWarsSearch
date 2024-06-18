package com.sf.starwarssearch.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sf.starwarssearch.R
import com.sf.starwarssearch.domain.model.FilmsModel
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.PlanetModel
import com.sf.starwarssearch.domain.model.SpeciesModel
import com.sf.starwarssearch.ui.search.ShowLoadingView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(people: PeopleItemModel, upPress: () -> Unit) {
    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getPeopleDetail(people.species, people.films, people.homeworld)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(id = R.string.detail_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.inversePrimary),
            navigationIcon = { Up(upPress) })
    }) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValue.calculateTopPadding(),
                    bottom = paddingValue.calculateBottomPadding()
                )
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
        ) {
            Body(people = people, state = state)
        }
    }
}


@Composable
fun Body(people: PeopleItemModel, state: PeopleDataState?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp), shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item { PeopleData(people = people) }
            when (state?.detailDisplay) {
                DetailDisplay.Loading -> {
                    item {
                        ShowLoadingView()
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }

                DetailDisplay.Result -> {
                    state.peopleDetailResults?.let {
                        it.species?.let { specieModels -> item { SpeciesData(specieModels) } }
                        it.planetPopulation?.let { planetModels -> item { PlanetData(planetModels) } }
                        it.films?.let { filmsModels -> item { FilmsData(filmsModels) } }
                    }
                }

                DetailDisplay.Error -> {
                    item { ShowErrorView() }
                }

                else -> {}
            }
        }
    }
}


@Composable
fun PeopleData(people: PeopleItemModel) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.people_section),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        )


        Text(
            text = stringResource(id = R.string.name, people.name),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.height, people.height ?: ""),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.birth_year, people.birth_year ?: ""),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun FilmsData(films: List<FilmsModel>) {
    if (films.isNotEmpty()) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.film_section),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
            )
            films.forEach { film ->
                film.title?.let {
                    FilmDetail(
                        filmsModel = film,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun FilmDetail(
    filmsModel: FilmsModel,
    modifier: Modifier
) {
    if (!filmsModel.title.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.title, filmsModel.title),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.fillMaxWidth()
        )
    }

    if (!filmsModel.opening_crawl.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.detail, filmsModel.opening_crawl),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.fillMaxWidth()
        )
    }
}


@Composable
fun SpeciesData(species: List<SpeciesModel>) {
    if (species.isNotEmpty()) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.species_section),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(vertical = 2.dp)
            )


            species.forEach { specie ->
                val modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                specie.name?.let { name -> SpeciesNameItem(name, modifier = modifier) }
                specie.language?.let { language ->
                    SpeciesLanguageItem(
                        language = language,
                        modifier = modifier
                    )
                }
                specie.homeworld?.let { homeWorld ->
                    SpeciesHomeWorldItem(
                        homeWorld = homeWorld,
                        modifier = modifier
                    )
                }
            }
        }
    }
}


@Composable
fun SpeciesNameItem(name: String, modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.name, name),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
    )
}


@Composable
fun SpeciesLanguageItem(language: String, modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.language, language),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
    )
}


@Composable
fun SpeciesHomeWorldItem(homeWorld: String, modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.home_world, homeWorld),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
    )
}


@Composable
fun PlanetData(planets: List<PlanetModel>) {
    if (planets.isNotEmpty()) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.planet_section),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(vertical = 2.dp)
            )


            planets.forEach { planet ->
                planet.population?.let {
                    Text(
                        text = stringResource(id = R.string.population, planet.population),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ShowErrorView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
        Text(
            text = stringResource(id = R.string.error_occurred),
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 2.dp)
            .size(height = 32.dp, width = 42.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}