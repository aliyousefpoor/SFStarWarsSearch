package com.sf.starwarssearch.ui.detail

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sf.starwarssearch.R
import com.sf.starwarssearch.domain.model.FilmsModel
import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.PlanetModel
import com.sf.starwarssearch.domain.model.SpeciesModel


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
            Body(people, state)
        }
    }
}


@Composable
fun Body(people: PeopleItemModel, state: PeopleDetailModel?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(Modifier.fillMaxWidth()) {
            item { PeopleData(people = people) }
            state?.let {
                it.species?.let { specieModels -> item { SpeciesData(specieModels) } }
                it.planetPopulation?.let { planetModels -> item { PlanetData(planetModels) } }
                it.films?.let { filmsModels -> item { FilmsData(filmsModels) } }
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
    films.let {
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
    species.let {
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
fun PlanetData(planets: List<PlanetModel>) {
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
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 2.dp)
            .size(height = 32.dp, width = 42.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            tint = colorResource(id = R.color.white),
            contentDescription = stringResource(R.string.label_back)
        )
    }
}