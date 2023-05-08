package com.odukabdulbasit.movieradar.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.odukabdulbasit.movieradar.model.Movie

@Entity(tableName = "databasemovie")
data class DatabaseMovie constructor(
    @PrimaryKey
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
    @TypeConverters val genre_ids: List<Int>
)


//convert from database objects to domain objects
fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
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