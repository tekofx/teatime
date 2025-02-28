package dev.tekofx.teatime

import androidx.lifecycle.ViewModel
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import com.kdroid.composenotification.builder.Notification
import dev.tekofx.teatime.model.Tea
import dev.tekofx.teatime.model.TimerManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res
import androidx.lifecycle.viewModelScope

class AppViewModel(private val timerManager: TimerManager):ViewModel() {
    private var timerJob: Job? = null
    private val _activeTea = MutableStateFlow<Tea?>(null)
    val activeTea = _activeTea.asStateFlow()
    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()
    private val _formattedTime = MutableStateFlow("0:00")
    val formattedTime = _formattedTime.asStateFlow()

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

    fun setActiveTea(tea: Tea) {
        startTimer(tea)
    }


    private fun formatSeconds(seconds: Long) {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        _formattedTime.value=String.format("%d:%02d", minutes, remainingSeconds)
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalNotificationsApi::class)
    private fun startTimer(tea: Tea) {
        if (_activeTea.value?.id == tea.id) {
            timerJob?.cancel()
            _activeTea.value = null
            return
        } else {
            _activeTea.value = tea
        }

        _timer.value=tea.time.toLong()
        formatSeconds(_timer.value)
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timer.value>0L) {
                delay(1000)
                _timer.value--
                formatSeconds(_timer.value)
            }
            Notification(
                title = "Timer Finished",
                message = "Timer for ${tea.name} finished",
                largeImage = Res.getUri("drawable/tea.png")
            )
            _activeTea.value=null

        }


    }
}