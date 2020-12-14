package pl.krzysztofruczkowski.pwr3.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr3.BmiPersistence
import pl.krzysztofruczkowski.pwr3.R
import pl.krzysztofruczkowski.pwr3.RecordsAdapter
import pl.krzysztofruczkowski.pwr3.database.RecordDatabase
import pl.krzysztofruczkowski.pwr3.models.Record

class BmiHistoryActivity : AppCompatActivity() {
    private lateinit var records: List<Record>
    private lateinit var db: RecordDatabase

    private fun getRecords(): List<Record> = BmiPersistence(db.recordDatabaseDao).getBmiRecords().map {it.toRecord()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = RecordDatabase.getInstance(applicationContext)

        setContentView(R.layout.activity_bmi_history)
        val rvRecords = findViewById<View>(R.id.rvRecords) as RecyclerView
        records = getRecords()
        val adapter = RecordsAdapter(records)
        rvRecords.adapter = adapter
        rvRecords.layoutManager = LinearLayoutManager(this)
    }
}
