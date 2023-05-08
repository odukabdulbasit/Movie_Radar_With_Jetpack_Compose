package com.odukabdulbasit.movieradar.data

import android.content.Context
import com.odukabdulbasit.movieradar.Constants
import com.odukabdulbasit.movieradar.network.MovieApiService
import com.odukabdulbasit.movieradar.roomdatabase.MoviesDatabase
import com.odukabdulbasit.movieradar.roomdatabase.getDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val movieRepository : MovieRepository
    val database: MoviesDatabase
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

class DefaultAppContainer(private val context: Context): AppContainer{

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()


    /**
     * Retrofit service object for creating api calls
     */

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }


    override val database: MoviesDatabase
        get() = getDatabase(context)

    override val movieRepository: MovieRepository by lazy {
        NetworkMovieRepository(retrofitService, database)
    }


}