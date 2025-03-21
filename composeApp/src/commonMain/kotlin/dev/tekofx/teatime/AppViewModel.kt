package dev.tekofx.teatime

import androidx.lifecycle.ViewModel
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import com.kdroid.composenotification.builder.Notification
import dev.tekofx.teatime.model.Tea
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res
import androidx.lifecycle.viewModelScope

class AppViewModel() : ViewModel() {
    private var timerJob: Job? = null
    private val _activeTea = MutableStateFlow<Tea?>(null)
    val activeTea = _activeTea.asStateFlow()
    private val _timer = MutableStateFlow(0L)
    private val _formattedTime = MutableStateFlow("0:00")
    val formattedTime = _formattedTime.asStateFlow()

    private val _teas = MutableStateFlow(
        listOf(
            Tea(0, "Green Tea", 80, 2, 30, "1 cup"),
            Tea(2, "Oolong Tea", 100, 4, 0, "1 cup"),
            Tea(1, "Pu'er", 95, 4, 30, "1 cup"),
            Tea(1, "Black Tea", 100, 4, 0, "1 cup"),
            Tea(3, "White Tea", 80, 5, 0, "1 cup"),
            Tea(4, "Test", 70, 0, 5, "1 cup"),
        )
    )
    val teas = _teas.asStateFlow()


    private fun formatSeconds(seconds: Long) {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        _formattedTime.value = String.format("%d:%02d", minutes, remainingSeconds)
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalNotificationsApi::class)
    fun startTimer(tea: Tea) {
        if (_activeTea.value?.id == tea.id) {
            timerJob?.cancel()
            _activeTea.value = null
            _formattedTime.value = "0:00"
            return
        }

        _activeTea.value = tea
        _timer.value = tea.time.toLong()
        formatSeconds(_timer.value)
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timer.value > 0L) {
                delay(1000)
                _timer.value--
                formatSeconds(_timer.value)
            }
            Notification(
                title = "Timer Finished",
                message = "Timer for ${tea.name} finished",
                largeImage = Res.getUri("drawable/tea.png")
            )
            _activeTea.value = null
        }
    }
}