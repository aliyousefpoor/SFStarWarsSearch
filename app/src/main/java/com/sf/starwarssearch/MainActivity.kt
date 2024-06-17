package com.sf.starwarssearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sf.starwarssearch.ui.search.SearchScreen
import com.sf.starwarssearch.ui.search.SearchViewModel
import com.sf.starwarssearch.ui.theme.StarWarsSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.white)
                ) {
                    ShowSearchScreen()
                }
            }
        }
    }
}

@Composable
fun ShowSearchScreen() {
    val viewModel: SearchViewModel = hiltViewModel()
    SearchScreen(viewModel = viewModel)
}