package com.sf.starwarssearch.ui.search


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sf.starwarssearch.R
import com.sf.starwarssearch.domain.model.PeopleItemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Search") })
    }, snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->

        Column(modifier = Modifier.padding(16.dp)) {
            var query by remember { mutableStateOf("") }
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.blue),
                    unfocusedBorderColor = colorResource(id = R.color.gray_200),
                    focusedLabelColor = colorResource(id = R.color.blue),
                    cursorColor = colorResource(id = R.color.blue),
                    focusedTextColor = colorResource(id = R.color.gray_200),
                    errorBorderColor = colorResource(id = R.color.red),
                    errorLabelColor = colorResource(id = R.color.red),
                    errorCursorColor = colorResource(id = R.color.red),
                    errorTextColor = colorResource(id = R.color.gray_200)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = null
                    )
                },
                value = query,
                label = {
                    Text(text = "Search")
                },
                onValueChange = {
                    query = it
                },
                isError = ((state is SearchState.EmptyState) || (state is SearchState.ErrorState)),
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.handleSearchIntent(SearchIntent.Search(query)) },
                enabled = query.isNotEmpty()
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (state) {
                is SearchState.LoadingState -> {
                    if ((state as SearchState.LoadingState).isLoading) ShowLoadingView()
                }

                is SearchState.ResultState -> {
                    val result = (state as SearchState.ResultState).result
                    result.let { results ->
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
                                        .clickable { }, people
                                )
                            }
                        }
                    }
                }

                is SearchState.EmptyState -> {
                    NoResultView()
                }

                is SearchState.ErrorState -> {}
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
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
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
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    )

                    Text(
                        text = people.birth_year,
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

@Composable
fun NoResultView() {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.no_result_found))
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
        CircularProgressIndicator()
    }
}