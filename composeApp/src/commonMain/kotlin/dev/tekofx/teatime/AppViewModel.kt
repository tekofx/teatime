package dev.tekofx.teatime

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import com.kdroid.composenotification.builder.Notification
import dev.tekofx.teatime.model.Tea
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res

class AppViewModel() {
    private var timerJob: Job? = null

    val activeTea = MutableStateFlow<Tea?>(null)

    private val _teas = MutableStateFlow<List<Tea>>(
        listOf(
            Tea(0, "Green Tea", 80, 3, 0, "1 cup"),
            Tea(1, "Black Tea", 100, 3, 0, "1 cup"),
            Tea(2, "Oolong Tea", 90, 3, 0, "1 cup"),
            Tea(3, "White Tea", 70, 3, 0, "1 cup"),
            Tea(4, "Test", 70, 0, 5, "1 cup"),

        )
    )
    val teas = _teas.asStateFlow()

    private var remainingTime by mutableStateOf(0L)

    val formattedRemainingTime: String
        get() = formatTime(remainingTime)

    fun setActiveTea(tea: Tea) {
        startTimer(tea)
    }

    private fun formatTime(timeInMillis: Long): String {
        val totalSeconds = timeInMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalNotificationsApi::class)
    private fun startTimer(tea: Tea) {
        if (activeTea.value?.id == tea.id) {
            timerJob?.cancel()
            activeTea.value = null
            remainingTime = 0
            return
        } else {
            activeTea.value = tea
            Notification(
                title = "Timer Started",
                message = "Timer for ${tea.name} started",
                largeImage = Res.getUri("drawable/tea.png")
            )
        }


        val timeInMillis = tea.time.toLong() * 1000
        remainingTime = timeInMillis

        timerJob?.cancel()
        timerJob = CoroutineScope(Dispatchers.Main).launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
            }
            Notification(
                title = "Timer Finished",
                message = "Timer for ${tea.name} finished",
                largeImage = Res.getUri("drawable/tea.png")
            )
            activeTea.value = null
        }
    }
}