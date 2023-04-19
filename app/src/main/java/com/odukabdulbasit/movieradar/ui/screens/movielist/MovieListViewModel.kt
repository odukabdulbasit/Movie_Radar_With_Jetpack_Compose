package com.odukabdulbasit.movieradar.ui.screens.movielist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.odukabdulbasit.movieradar.MovieApplication
import com.odukabdulbasit.movieradar.data.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.odukabdulbasit.movieradar.model.MovieObjects

sealed interface MovieUiState{

    data class Success(val movies : MovieObjects): MovieUiState
    object Error: MovieUiState
    object Loading: MovieUiState
}

class MovieListViewModel(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set


    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading

            movieUiState = try {
                MovieUiState.Success(movieRepository.getMovies())
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }


    /*companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                MovieListViewModel(movieRepository = movieRepository)
            }
        }
    }*/


    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return MovieListViewModel(
                    (application as MovieApplication).container.movieRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}