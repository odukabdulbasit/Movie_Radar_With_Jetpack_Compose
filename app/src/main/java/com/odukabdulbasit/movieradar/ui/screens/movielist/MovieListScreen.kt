package com.odukabdulbasit.movieradar.ui.screens.movielist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.odukabdulbasit.movieradar.R
import com.odukabdulbasit.movieradar.model.Movie

@Composable
fun MovieListScreen(
    movies: List<Movie>,
    retryAction: () -> Unit,
    onListItemClicked: (id: Int) -> Unit,
){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(movies){ item ->
            MovieListItem(item, onListItemClicked)
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onItemClick: (id: Int) -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(13.dp)
            .clickable {
                onItemClick(movie.id)
            }
    ) {

        Text(
            text = movie.title,
            modifier = Modifier.padding(start = 5.dp, top = 10.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
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
