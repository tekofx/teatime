package dev.tekofx.teatime

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import com.kdroid.composenotification.builder.Notification
import dev.tekofx.teatime.model.TimerManager
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res

class TimerWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @OptIn(ExperimentalResourceApi::class, ExperimentalNotificationsApi::class)
    override suspend fun doWork(): Result {
        val teaName = inputData.getString("teaName") ?: return Result.failure()
        val timeInMillis = inputData.getLong("timeInMillis", 0L)

        var remainingTime = timeInMillis
        while (remainingTime > 0) {
            delay(1000)
            remainingTime -= 1000
        }

        Notification(
            title = "Timer Finished",
            message = "Timer for $teaName finished",
            largeImage = Res.getUri("drawable/tea.png")
        )

        return Result.success()
    }
}

class AndroidTimerManager(private val context: Context):TimerManager {
    override fun startTimer(teaName: String, timeInMillis: Long) {
        val workRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setInputData(
                Data.Builder()
                    .putString("teaName", teaName)
                    .putLong("timeInMillis", timeInMillis)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }


}
