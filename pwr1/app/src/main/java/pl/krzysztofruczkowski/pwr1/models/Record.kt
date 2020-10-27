package pl.krzysztofruczkowski.pwr1.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Record(val bmi: Double, val date: String)
