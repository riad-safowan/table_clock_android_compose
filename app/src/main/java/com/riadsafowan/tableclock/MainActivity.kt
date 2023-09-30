package com.riadsafowan.tableclock

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.riadsafowan.tableclock.ui.theme.TableClockTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        if (windowInsetsController != null) {
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                view.onApplyWindowInsets(windowInsets)
            }
        }
        setContent {
            TableClockTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.Black,
                ) {
                    TimeDisplay()
                }
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }
}

@Composable
fun TimeDisplay() {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val currentTime = remember { mutableStateOf("00:00") }

    LaunchedEffect(true) {
        while (true) {
            val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
            val time = dateFormat.format(Date())
            currentTime.value = time
            delay(1000) // Update every second
        }
    }

    if (isPortrait) {
        // Display the first composable for portrait orientation
        ShowVerticalTime(currentTime.value)
    } else {
        ShowHorizontalTime(currentTime.value)
    }

}

@Composable
fun ShowHorizontalTime(currentTime: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .offset((-10).dp, (-20).dp)

    ) {
        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 320.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = Color.Black),
        )
    }
}

@Composable
fun ShowVerticalTime(currentTime: String) {
    val (hours, minutes) = currentTime.split(":")
    val size = 320.sp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = hours,
            color = Color.White,
            fontSize = size,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .background(color = Color.Black)
                .offset((3).dp, (20).dp),
        )
        Text(
            text = minutes,
            color = Color.White,
            fontSize = size,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .background(color = Color.Black)
                .offset((3).dp, (-80).dp),
        )
    }
}

@Composable
fun TransparentOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    )
}
