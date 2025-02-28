package dev.tekofx.teatime

import com.kdroid.composenotification.builder.Notification
import dev.tekofx.teatime.model.TimerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res

class DesktopTimerManager : TimerManager {
    private var timerJob: Job? = null

    @OptIn(ExperimentalResourceApi::class)
    override fun startTimer(teaName: String, timeInMillis: Long) {
        var mutableTimeInMillis = timeInMillis
        timerJob?.cancel()
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (mutableTimeInMillis > 0) {
                delay(1000)
                mutableTimeInMillis -= 1000
            }
            Notification(
                title = "Timer Finished",
                message = "Timer for $teaName finished",
                largeImage = Res.getUri("drawable/tea.png")
            )
            println("Timer for $teaName finished")
        }
    }
}