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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getPeopleDetail(people.species, people.films, people.homeworld)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                "Detail", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }, navigationIcon = { Up(upPress) })
    }) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValue.calculateTopPadding(),
                    bottom = paddingValue.calculateBottomPadding()
                )
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
            .padding(8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(Modifier.fillMaxWidth()) {
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
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Name: ${people.name}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Height: ${people.height} cm",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colorResource(id = R.color.gray_100),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Birth Date: ${people.birth_year}",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun FilmsData(films: List<FilmsModel>) {
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Film Section:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        )
        films.forEach { film ->
            film.title?.let { FilmDetail(film) }
            Spacer(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun FilmDetail(filmsEntity: FilmsModel) {
    Text(
        text = "Title : ${filmsEntity.title}",
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.gray_100),
        modifier = Modifier.padding(vertical = 2.dp)
    )


    Text(
        text = "Detail : ${filmsEntity.opening_crawl?.replace("\n", "")}",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.gray_100),
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
    )
}


@Composable
fun SpeciesData(species: List<SpeciesModel>) {
    Column(Modifier.padding(8.dp)) {
        Text(
            text = "Species Section:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(vertical = 2.dp)
        )

        species.forEach { specie ->
            specie.name?.let { name -> SpeciesNameItem(name) }
            specie.language?.let { language -> SpeciesLanguageItem(language = language) }
            specie.homeworld?.let { homeWorld -> SpeciesHomeWorldItem(homeWorld = homeWorld) }
        }
    }
}


@Composable
fun SpeciesNameItem(name: String) {
    Text(
        text = "Name :$name",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.gray_100),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}


@Composable
fun SpeciesLanguageItem(language: String) {
    Text(
        text = "Language : $language",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.gray_100),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}


@Composable
fun SpeciesHomeWorldItem(homeWorld: String) {
    Text(
        text = "HomeWorld : $homeWorld",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.gray_100),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}


@Composable
fun PlanetData(planets: List<PlanetModel>?) {
    planets?.let {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Planet Section:",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.white),
                modifier = Modifier.padding(vertical = 2.dp)
            )

            planets.forEach { planet ->
                planet.population?.let {
                    Text(
                        text = "Population : ${planet.population}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = colorResource(id = R.color.gray_100),
                        modifier = Modifier.padding(vertical = 2.dp)
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
            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
        )
        Text(
            text = "error Occurred",
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
            tint = colorResource(id = R.color.white),
            contentDescription = stringResource(R.string.label_back)
        )
    }
}