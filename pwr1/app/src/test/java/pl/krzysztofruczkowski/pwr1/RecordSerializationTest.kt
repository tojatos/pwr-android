package pl.krzysztofruczkowski.pwr1

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr1.models.Record
import java.util.*

class RecordSerializationTest : FunSpec({
    test("record can be serialized and deserialized") {
        val record = Record(15.0, Date().toString(), 123.0, 123.0, "cm", "kg")
        val recordJson = Json.encodeToString(record)
        print(recordJson)
        val recordDeserialized = Json.decodeFromString<Record>(recordJson)

        record shouldBe recordDeserialized
    }

    test("records can be serialized and deserialized") {
        val records = listOf(
            Record(15.0, Date().toString(), 123.0, 111.0, "cm", "kg"),
            Record(12.4, "test", 123.0, 111.0, "df", "sdf"),
        )
        val recordsJson = Json.encodeToString(records)
        print(recordsJson)
        val recordsDeserialized = Json.decodeFromString<List<Record>>(recordsJson)

        records shouldBe recordsDeserialized
    }

    test("empty record list can be serialized and deserialized") {
        val records = listOf<Record>()
        val recordsJson = Json.encodeToString(records)
        print(recordsJson)
        val recordsDeserialized = Json.decodeFromString<List<Record>>(recordsJson)

        records shouldBe recordsDeserialized
    }
})