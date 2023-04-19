package com.odukabdulbasit.movieradar.data

import com.odukabdulbasit.movieradar.Constants
import com.odukabdulbasit.movieradar.model.MovieObjects
import com.odukabdulbasit.movieradar.network.MovieApiService

interface MovieRepository {
    suspend fun getMovies(): MovieObjects
}


class NetworkMovieRepository(
    private val movieApiService: MovieApiService
): MovieRepository{

    override suspend fun getMovies(): MovieObjects = movieApiService.getMovieList(Constants.apiKey)
}