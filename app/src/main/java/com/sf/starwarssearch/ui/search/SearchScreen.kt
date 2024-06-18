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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sf.starwarssearch.R
import com.sf.starwarssearch.domain.model.PeopleItemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navHostController: NavHostController) {
    val viewModel: SearchViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    var isAtEnd by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.search_title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.inversePrimary)
        )
    }, snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
        ) {
            var query by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.outline,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    errorTextColor = MaterialTheme.colorScheme.onPrimary
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                value = query,
                label = {
                    Text(text = stringResource(id = R.string.search_title))
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
                onClick = {
                    viewModel.getSearchResult(query)
                    keyboardController?.hide()
                },
                enabled = query.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline)
            ) {
                Text(
                    text = stringResource(id = R.string.search_title),
                    color = colorResource(id = R.color.white)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (state.searchDisplay) {
                SearchDisplay.Loading -> {
                    if (state.isLoading) ShowLoadingView()
                }

                SearchDisplay.Result -> {
                    val result = state.searchResults
                    LaunchedEffect(listState) {
                        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
                            .collect { lastVisibleItem ->
                                val lastIndex = result?.results?.size?.minus(1)
                                isAtEnd = lastVisibleItem?.index == lastIndex
                            }
                    }
                    if (isAtEnd && !result?.results.isNullOrEmpty()) {
                        viewModel.getSearchResult(query)
                    }
                    if (!result?.results.isNullOrEmpty()) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                        ) {
                            item {
                                SearchItemCount(result?.count!!)
                            }

                            items(
                                items = result?.results!!,
                                key = { it.url!! }) { people ->
                                Spacer(modifier = Modifier.padding(top = 8.dp))
                                SearchResultItem(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp), people
                                ) {
                                    val encodedJson = viewModel.preparePeopleModel(people)
                                    navHostController.navigate(route = "detail/$encodedJson")
                                }
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
fun SearchItemCount(count: Int) {
    Text(
        text = stringResource(id = R.string.item_count, count),
        modifier = Modifier.wrapContentWidth(),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
fun SearchResultItem(modifier: Modifier, people: PeopleItemModel, onClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onClick.invoke()
                },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onSecondary)
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
                        text = stringResource(id = R.string.name, ""),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Text(
                        text = people.name,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary,
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
                        text = stringResource(id = R.string.birth_date),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Text(
                        text = people.birth_year,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colorScheme.onPrimary,
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
                            text = stringResource(id = R.string.film_count),
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 2.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )

                        Text(
                            text = "${people.films.size}",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 2.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onPrimary,
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
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
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
            color = MaterialTheme.colorScheme.outline
        )
    }
}