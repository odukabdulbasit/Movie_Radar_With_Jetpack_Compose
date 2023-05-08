package com.odukabdulbasit.movieradar.ui.screens.movierecommandation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odukabdulbasit.movieradar.R

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MovieRecommendationScreen(
    onDeviceShaked: () -> Unit
) {
    ShakeToRecommendScreen(onDeviceShaked)
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ShakeToRecommendScreen(onDeviceShaked: () -> Unit) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(SensorManager::class.java)
    val shakeThreshold = 800 // Adjust this value as needed

    var isShaking by remember { mutableStateOf(false) }

    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not needed for shake detection
            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    val sides = event.values[0]

                    val upDown = event.values[1]
                    if (upDown.toInt() > 4 && sides.toInt() > 4) {

                        isShaking = true
                        onDeviceShaked()
                        Log.i("isShaking", "isShaking")
                    } else {
                        isShaking = false
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.please_shake_the_phone_to_get_film_recommendation),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}









