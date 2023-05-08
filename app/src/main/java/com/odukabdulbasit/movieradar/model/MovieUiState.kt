package com.odukabdulbasit.movieradar.model

sealed interface MovieUiState{

    data class Success(val movies: List<Movie>): MovieUiState
    object Error: MovieUiState
    object Loading: MovieUiState
}