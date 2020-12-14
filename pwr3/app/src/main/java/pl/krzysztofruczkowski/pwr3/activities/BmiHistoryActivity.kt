package pl.krzysztofruczkowski.pwr3.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr3.R
import pl.krzysztofruczkowski.pwr3.RecordsAdapter
import pl.krzysztofruczkowski.pwr3.models.Record

class BmiHistoryActivity : AppCompatActivity() {
    private lateinit var records: List<Record>

    private fun getRecords(): List<Record> {
        val sharedPref = getSharedPreferences(MainActivity.RECORDS_FILE_KEY, Context.MODE_PRIVATE) ?: return emptyList()
        val recordsString = sharedPref.getString(MainActivity.SAVED_RECORDS_KEY, "[]").toString()
        return Json.decodeFromString(recordsString)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bmi_history)

        val rvRecords = findViewById<View>(R.id.rvRecords) as RecyclerView
        records = getRecords()
        val adapter = RecordsAdapter(records)
        rvRecords.adapter = adapter
        rvRecords.layoutManager = LinearLayoutManager(this)
    }
}
