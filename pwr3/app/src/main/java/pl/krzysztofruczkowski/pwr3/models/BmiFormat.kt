package pl.krzysztofruczkowski.pwr3.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
enum class BmiFormat {
    EUROPEAN, AMERICAN;
    companion object {
        fun serialize(format: BmiFormat) = Json.encodeToString(format)
        fun deserialize(formatString: String) = Json.decodeFromString<BmiFormat>(formatString)
    }
}
