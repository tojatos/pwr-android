package pl.krzysztofruczkowski.pwr1.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr1.R
import pl.krzysztofruczkowski.pwr1.RecordsAdapter
import pl.krzysztofruczkowski.pwr1.databinding.ActivityMainBinding
import pl.krzysztofruczkowski.pwr1.models.Record
import java.util.stream.Collectors.toList

class BmiHistoryActivity : AppCompatActivity() {
    private lateinit var records: List<Record>

    private fun getRecords(): List<Record> {
        val sharedPref = getSharedPreferences(getString(R.string.records_file_key), Context.MODE_PRIVATE) ?: return emptyList()
        val recordsString = sharedPref.getString(getString(R.string.saved_records_key), "[]").toString()
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