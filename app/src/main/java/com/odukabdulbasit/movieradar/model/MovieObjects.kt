package com.odukabdulbasit.movieradar.model

import com.odukabdulbasit.movieradar.network.NetworkMovie
import com.squareup.moshi.Json

data class MovieObjects(
    val page : Int,
    @Json(name = "results") val movieList : List<NetworkMovie>,
    val total_results: Int,
    val total_pages: Int
)


data class Movie(
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