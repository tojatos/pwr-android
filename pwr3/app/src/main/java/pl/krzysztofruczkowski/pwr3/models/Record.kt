package pl.krzysztofruczkowski.pwr3.models

import kotlinx.serialization.Serializable
import pl.krzysztofruczkowski.pwr3.database.RecordEntity

@Serializable
data class Record(
    val bmi: Double,
    val date: String,
    val height: Double,
    val mass: Double,
    val heightUnit: String,
    val massUnit: String,
) {
    fun toEntity() = RecordEntity(0L, bmi, date, height, mass, heightUnit, massUnit)
}

