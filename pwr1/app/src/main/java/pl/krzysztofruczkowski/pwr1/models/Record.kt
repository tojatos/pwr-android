package pl.krzysztofruczkowski.pwr1.models

import kotlinx.serialization.Serializable

@Serializable
data class Record(
    val bmi: Double,
    val date: String,
    val height: Double,
    val mass: Double,
    val heightUnit: String,
    val massUnit: String,
)
