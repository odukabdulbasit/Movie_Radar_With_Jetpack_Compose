package com.odukabdulbasit.movieradar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.odukabdulbasit.movieradar.ui.MovieApp
import com.odukabdulbasit.movieradar.ui.theme.MovieRadarTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieRadarTheme {
                MovieApp(context = this)
            }
        }
    }
}


