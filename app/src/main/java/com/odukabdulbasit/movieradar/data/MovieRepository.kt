package com.odukabdulbasit.movieradar.data

import com.odukabdulbasit.movieradar.Constants.apiKey
import com.odukabdulbasit.movieradar.model.Movie
import com.odukabdulbasit.movieradar.model.MovieObjects
import com.odukabdulbasit.movieradar.network.MovieApiService
import com.odukabdulbasit.movieradar.network.NetworkMovieContainer
import com.odukabdulbasit.movieradar.network.asDatabaseModel
import com.odukabdulbasit.movieradar.roomdatabase.MoviesDatabase
import com.odukabdulbasit.movieradar.roomdatabase.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface MovieRepository {
    suspend fun getMovies(): MovieObjects
    suspend fun refreshMovies()
    suspend fun getRandomMovie(randomNumber: Int): Flow<Movie>
    suspend fun isDataSavedInDatabase(): Boolean
    val movies: Flow<List<Movie>>
}


class NetworkMovieRepository(
    private val movieApiService: MovieApiService,
    private val database: MoviesDatabase
): MovieRepository{

    override val movies: Flow<List<Movie>> = database.movieDao.getMovies()
        .map { it.asDomainModel() }

    override suspend fun getRandomMovie(randomNumber: Int): Flow<Movie> =
        database.movieDao.getMovies()
            .map { it.asDomainModel()[randomNumber] }

    override suspend fun getMovies(): MovieObjects = movieApiService.getMovieList(apiKey)

    override suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            val movieList = movieApiService.getMovieList(apiKey).movieList
            database.movieDao.insertAll(NetworkMovieContainer(movieList).asDatabaseModel().toList())
        }
    }



    override suspend fun isDataSavedInDatabase(): Boolean {
        val movieCount = database.movieDao.getMovieCount()
        return movieCount > 0
    }
}