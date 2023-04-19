package com.odukabdulbasit.movieradar.network

import com.odukabdulbasit.movieradar.model.MovieObjects
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService{

    @GET("/3/movie/popular")
    suspend fun getMovieList(
       @Query("api_key") apiKey : String,
    ) : MovieObjects
}