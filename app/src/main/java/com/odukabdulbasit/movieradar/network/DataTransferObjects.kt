package com.odukabdulbasit.movieradar.network


import com.odukabdulbasit.movieradar.model.Movie
import com.odukabdulbasit.movieradar.roomdatabase.DatabaseMovie
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val movies: List<NetworkMovie>)

/**
 * Movies represent a movies from themoviedb.
 */
@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val release_date: String? = "",
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>
)

/**
 * Convert Network results to database objects
 */
fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return movies.map {
        Movie(
            id = it.id,
            title = it.title,
            poster_path = it.poster_path,
            original_language = it.original_language,
            original_title = it.original_title,
            overview = it.overview,
            popularity = it.popularity,
            release_date = it.release_date,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count,
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            genre_ids = it.genre_ids
        )
    }
}


// converts from data transfer objects to database objects
fun NetworkMovieContainer.asDatabaseModel(): Array<DatabaseMovie>{
    return movies.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            poster_path = it.poster_path,
            original_language = it.original_language,
            original_title = it.original_title,
            overview = it.overview,
            popularity = it.popularity,
            release_date = it.release_date,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count,
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            genre_ids = it.genre_ids
        )
    }.toTypedArray()
}