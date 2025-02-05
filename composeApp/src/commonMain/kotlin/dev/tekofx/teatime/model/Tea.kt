package dev.tekofx.teatime.model

data class Tea(
    val id: Int,
    val name: String,
    val temperature: Int,
    val minutes: Int,
    val seconds: Int,
    val quantity: String,
){
    val time: Int
        get() = minutes * 60 + seconds

    val formattedTime: String=
        String.format("%d:%02d", minutes, seconds)
}