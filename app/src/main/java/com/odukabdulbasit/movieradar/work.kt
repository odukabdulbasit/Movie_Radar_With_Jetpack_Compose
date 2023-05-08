package com.odukabdulbasit.movieradar

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.odukabdulbasit.movieradar.data.MovieRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters,
                        private val movieRepository: MovieRepository):
    CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }


    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {

        return  try {
            movieRepository.getMovies()
            Result.Success()
        }catch (e: HttpException){
            Result.retry()
        }
    }

}