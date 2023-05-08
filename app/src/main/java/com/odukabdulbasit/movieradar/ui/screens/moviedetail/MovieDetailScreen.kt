package com.odukabdulbasit.movieradar.ui.screens.moviedetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.odukabdulbasit.movieradar.R
import com.odukabdulbasit.movieradar.model.Movie

@Composable
fun MovieDetailScreen(
    movie: Movie,
){
    DetailView(selectedMovie = movie)
}

@Composable
fun DetailView(selectedMovie: Movie){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.aspectRatio(16f / 9f)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500/${selectedMovie.poster_path}")
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    contentDescription = "Movie poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(425.dp),
                    contentScale = ContentScale.Crop)


                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    //Movie Name text
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = selectedMovie.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )


                    //Movie rate text
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        text = selectedMovie.vote_average.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                //Movie date text
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    text = selectedMovie.release_date ?: " - ",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            //Movie detail text
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                fontSize = 18.sp,
                text = selectedMovie.overview,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
