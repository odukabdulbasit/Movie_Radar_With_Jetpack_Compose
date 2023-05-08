package com.odukabdulbasit.movieradar.roomdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.odukabdulbasit.movieradar.Converters
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao{

    @Query("select * from databasemovie")
    fun getMovies(): Flow<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabaseMovie>)

    /**
     * I used this method to check if data saved to databse
    */
    @Query("SELECT COUNT(*) FROM databasemovie")
    suspend fun getMovieCount(): Int
}

@Database(entities = [DatabaseMovie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase(){
    abstract val movieDao : MovieDao
}

private lateinit var INSTANCE : MoviesDatabase

fun getDatabase(context: Context) : MoviesDatabase{
    synchronized(!::INSTANCE.isInitialized){
        INSTANCE = Room.databaseBuilder(context.applicationContext,
            MoviesDatabase::class.java,
            "movies").build()
    }

    return INSTANCE
}
