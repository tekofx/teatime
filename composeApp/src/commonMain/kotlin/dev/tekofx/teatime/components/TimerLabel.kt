package dev.tekofx.teatime.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerLabel(time: Long) {

    fun formatTime(timeInMillis: Long): String {
        val totalSeconds = timeInMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    Surface(
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = formatTime(time),
            fontSize = 80.sp
        )
    }
}