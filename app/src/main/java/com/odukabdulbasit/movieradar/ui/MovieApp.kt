package com.odukabdulbasit.movieradar.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odukabdulbasit.movieradar.ui.screens.movielist.MovieListScreen
import com.odukabdulbasit.movieradar.ui.screens.movielist.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(modifier: Modifier = Modifier){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = "Movie Radar") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colorScheme.background
            ) {

                val movieListViewModel: MovieListViewModel =
                    viewModel(factory = MovieListViewModel.Factory)


                MovieListScreen(
                    movieUiState = movieListViewModel.movieUiState,
                    retryAction = movieListViewModel::getMovies
                )
            }
        }
    )
}