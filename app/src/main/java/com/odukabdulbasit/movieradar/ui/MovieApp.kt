package com.odukabdulbasit.movieradar.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.odukabdulbasit.movieradar.model.Movie
import com.odukabdulbasit.movieradar.model.MovieUiState
import com.odukabdulbasit.movieradar.ui.screens.moviedetail.MovieDetailScreen
import com.odukabdulbasit.movieradar.ui.screens.movielist.MovieListScreen
import com.odukabdulbasit.movieradar.ui.screens.movielist.MovieListViewModel
import com.odukabdulbasit.movieradar.ui.screens.movierecommandation.MovieRecommendationScreen
import com.odukabdulbasit.movieradar.ui.theme.MovieRadarTheme

enum class MovieScreen(@StringRes val title: Int){
    MovieListScreen(title = com.odukabdulbasit.movieradar.R.string.app_name),
    MovieDetailScreen(title = com.odukabdulbasit.movieradar.R.string.movie_detail),
    MovieRecommendationScreen(title = com.odukabdulbasit.movieradar.R.string.movie_recommendation)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    context: Context,
    currentScreen: MovieScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onRecommendationClicked : () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            MovieMenu(context, onRecommendationClicked)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(com.odukabdulbasit.movieradar.R.string.back_button)
                    )
                }
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
    ){

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.MovieListScreen.name
    )


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MovieAppBar(
                context = context,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onRecommendationClicked = {
                    navController.navigate(MovieScreen.MovieRecommendationScreen.name)
                }
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

                var movies : List<Movie> = listOf()
                var movieToShowDetail by remember { mutableStateOf<Movie?>(null) }

                when(val movieUiState = movieListViewModel.movieUiState){
                    MovieUiState.Loading ->{}
                    is MovieUiState.Success -> {
                        movies = movieUiState.movies
                    }
                    MovieUiState.Error ->{}
                }

                NavHost(
                    navController = navController,
                    startDestination = MovieScreen.MovieListScreen.name,
                    modifier = modifier.padding(5.dp)
                ){

                    composable(route = MovieScreen.MovieListScreen.name){
                        MovieListScreen(
                                movies = movies,
                                retryAction = movieListViewModel::getMovieProperty
                            ) { movieId ->
                            movieToShowDetail = movies.find { movie ->  movie.id == movieId }
                                navController.navigate(MovieScreen.MovieDetailScreen.name)
                            }
                    }

                    composable(route = MovieScreen.MovieDetailScreen.name){
                        movieToShowDetail?.let { movieToShowDetail ->
                            MovieDetailScreen(movieToShowDetail)
                        }
                    }

                    composable(route = MovieScreen.MovieRecommendationScreen.name){
                        MovieRecommendationScreen(
                            onDeviceShaked = {
                                val recommendIndex = (0..movies.size.minus(1)).random()
                                movieToShowDetail = movies[recommendIndex]

                                navController.navigate(MovieScreen.MovieDetailScreen.name)
                            }
                        )
                    }
                }
            }
        }
    )
}




@Composable
fun MovieMenu(context: Context, onRecommendationClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(
            onClick = {
                expanded = true
                onRecommendationClicked()
            }
        ) {
            Icon(
                painter = painterResource(com.odukabdulbasit.movieradar.R.drawable.ic_baseline_sync_24),
                contentDescription = "Menu"
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieRadarTheme {
        MovieApp(LocalContext.current)
    }
}