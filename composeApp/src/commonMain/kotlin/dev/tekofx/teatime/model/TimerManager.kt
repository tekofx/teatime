package dev.tekofx.teatime.model

interface TimerManager {
    fun startTimer(teaName: String, timeInMillis: Long)
}