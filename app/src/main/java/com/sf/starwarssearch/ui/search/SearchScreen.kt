package com.sf.starwarssearch.ui.search


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.sf.starwarssearch.R
import com.sf.starwarssearch.domain.model.PeopleItemModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navHostController: NavHostController, viewModel: SearchViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                "Search",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        })
    }, snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->

        Column(modifier = Modifier.padding(16.dp)) {
            var query by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.blue),
                    unfocusedBorderColor = colorResource(id = R.color.white),
                    focusedLabelColor = colorResource(id = R.color.blue),
                    cursorColor = colorResource(id = R.color.blue),
                    focusedTextColor = colorResource(id = R.color.white),
                    errorBorderColor = colorResource(id = R.color.red),
                    errorLabelColor = colorResource(id = R.color.red),
                    errorCursorColor = colorResource(id = R.color.red),
                    errorTextColor = colorResource(id = R.color.white)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(
                            id = R.color.white
                        )
                    )
                },
                value = query,
                label = {
                    Text(text = "Search")
                },
                onValueChange = {
                    query = it
                },
                isError = state.isError || state.searchDisplay.name == SearchDisplay.NoResult.name,
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.getSearchResult(query) },
                enabled = query.isNotEmpty()
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (state.searchDisplay) {
                SearchDisplay.Loading -> {
                    if (state.isLoading) ShowLoadingView()
                }

                SearchDisplay.Result -> {
                    val result = state.searchResults
                    result?.let { results ->
                        LazyColumn(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
                            item {
                                Text(
                                    text = "${result.count} Items",
                                    modifier = Modifier.wrapContentWidth(),
                                    color = colorResource(id = R.color.white),
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            items(items = results.results, key = { it.name }) { people ->
                                Spacer(modifier = Modifier.padding(top = 8.dp))
                                SearchItem(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .clickable {
                                            val json = Gson().toJson(people)
                                            val encodedJson = URLEncoder.encode(json, "UTF-8")
                                            navHostController.navigate(route = "detail/$encodedJson")
                                        }, people
                                )
                            }
                        }
                    }
                }

                SearchDisplay.NoResult -> {
                    NoResultView()
                }

                SearchDisplay.Error -> {
                    NoResultView()
                }
            }
        }
    }
}


@Composable
fun SearchItem(modifier: Modifier, people: PeopleItemModel) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(contentColor = colorResource(id = R.color.white))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Name : ",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                    Text(
                        text = people.name,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BirthDate : ",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                    Text(
                        text = people.birth_year,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
                    )
                }

                if (people.films?.isNotEmpty() == true) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Films Count : ",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 2.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )

                        Text(
                            text = "${people.films.size}",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 2.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoResultView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = "",
            modifier = Modifier.size(80.dp),
            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
        )
        Text(
            text = stringResource(id = R.string.no_result_found),
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun ShowLoadingView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(top = 16.dp),
            color = colorResource(id = R.color.blue)
        )
    }
}