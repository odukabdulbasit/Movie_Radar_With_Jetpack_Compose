package com.odukabdulbasit.movieradar.ui.screens.movielist

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.odukabdulbasit.movieradar.MovieApplication
import com.odukabdulbasit.movieradar.data.MovieRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.odukabdulbasit.movieradar.model.MovieUiState
import kotlinx.coroutines.flow.first


class MovieListViewModel(
    app: Application,
    private val movieRepository: MovieRepository
    ): ViewModel(){

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        if (isNetworkAvailable(app)) {
            getMovieProperty()
        }else{
            viewModelScope.launch {
                if (movieRepository.isDataSavedInDatabase()) {
                    loadMoviesFromDatabase()
                } else {
                   //show alert dialog to show there is no data available
                }
            }
        }
    }

    private fun loadMoviesFromDatabase() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading

            movieUiState = try {
                val movies = movieRepository.movies.first()
                movies?.let { MovieUiState.Success(it) } ?: MovieUiState.Error
            } catch (e: Exception) {
                MovieUiState.Error
            }
        }
    }

    fun getMovieProperty() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading

            try {
                movieRepository.refreshMovies()
                loadMoviesFromDatabase()
            } catch (e: Exception) {
                movieUiState = MovieUiState.Error
            }
        }
    }

    private fun isNetworkAvailable(application: Application): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                MovieListViewModel(app = application, movieRepository = movieRepository)
            }
        }
    }
}