package com.odukabdulbasit.movieradar.ui.screens.movielist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.odukabdulbasit.movieradar.R
import com.odukabdulbasit.movieradar.model.Movie
import com.odukabdulbasit.movieradar.ui.theme.MovieRadarTheme

@Composable
fun MovieListScreen(
    movieUiState: MovieUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    var items = listOf<Movie>()

    when(movieUiState){
        MovieUiState.Loading ->{}
        is MovieUiState.Success -> {
            items = movieUiState.movies.movieList
        }
        MovieUiState.Error ->{}
    }
    //val items = listOf("Item 1", "Item 2", "Item 3", "Item 1", "Item 2", "Item 3")

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(items){ item ->
            Log.i("title and image = ", "title= ${item.title}, image = ${item.backdrop_path}")
            item.backdrop_path?.let { MovieListItem(filmName = item.title, image = "https://image.tmdb.org/t/p/w500/$it") }
        }
    }
}

@Composable
fun MovieListItem(filmName: String, image: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(13.dp)
    ) {

        Text(
            text = filmName,
            modifier = Modifier.padding(start = 5.dp, top = 10.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "Movie description image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieRadarTheme {
        MovieListScreen()
    }
}*/
