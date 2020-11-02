package pl.krzysztofruczkowski.pwr1

import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr1.activities.MainActivity
import pl.krzysztofruczkowski.pwr1.models.Record

class BmiPersistence(private val sharedPref: SharedPreferences) {

    fun saveBmiRecord(newRecord: Record) {
        val recordsString = sharedPref.getString(MainActivity.SAVED_RECORDS_KEY, "[]").toString()
        val records = Json.decodeFromString<List<Record>>(recordsString)
        val newRecordsList = (if (records.size < 10) records else records.drop(1)) + newRecord
        with(sharedPref.edit()) {
            putString(MainActivity.SAVED_RECORDS_KEY, Json.encodeToString(newRecordsList))
            apply()
        }
    }


}