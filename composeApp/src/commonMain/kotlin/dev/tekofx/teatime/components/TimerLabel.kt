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
fun TimerLabel(time: String) {
    Surface(
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = time,
            fontSize = 80.sp
        )
    }
}