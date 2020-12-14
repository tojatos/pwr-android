package pl.krzysztofruczkowski.pwr3.activities

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofruczkowski.pwr3.*
import pl.krzysztofruczkowski.pwr3.BmiUtils.getBmiColor
import pl.krzysztofruczkowski.pwr3.database.RecordDatabase
import pl.krzysztofruczkowski.pwr3.databinding.ActivityMainBinding
import pl.krzysztofruczkowski.pwr3.models.BmiFormat
import pl.krzysztofruczkowski.pwr3.models.Record
import java.text.DecimalFormat
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var app_resources: Resources
        const val FORMAT_KEY = "FORMAT"
        const val HEIGHT_KEY = "HEIGHT"
        const val MASS_KEY = "MASS"
        const val BMI_KEY = "BMI"
        const val RECORDS_FILE_KEY = "pl.krzysztofruczkowski.pwr3.records"
        const val SAVED_RECORDS_KEY = "SAVED_RECORDS"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var persistence: BmiPersistence
    private var format = BmiFormat.EUROPEAN
    private var height: Double = 0.0
    private var mass: Double = 0.0
    private var bmi: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            RecordDatabase::class.java, "database-name"
//        ).build()
        val db = RecordDatabase.getInstance(applicationContext)

        persistence = BmiPersistence(db.recordDatabaseDao)
        app_resources = resources
        val view = binding.root
        setContentView(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(FORMAT_KEY, BmiFormat.serialize(format))
            putDouble(HEIGHT_KEY, height)
            putDouble(MASS_KEY, mass)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        format = BmiFormat.deserialize(savedInstanceState.getString(FORMAT_KEY).toString())
        height = savedInstanceState.getDouble(HEIGHT_KEY)
        mass = savedInstanceState.getDouble(MASS_KEY)

        updateBmiFormatTexts()
        updateBmiText()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun count(view: View) {
        getDataFromTextFields()
        updateBmiText()

        val formatter: Format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val newRecord = Record(bmi, formatter.format(Date()), height, mass, BmiUtils.getHeightUnit(format), BmiUtils.getMassUnit(format))
        persistence.saveBmiRecord(newRecord)
    }

    private fun updateBmiText() {
        binding.apply {
            bmi = BmiUtils.calculateBmi(mass, height, format)
            bmiTV.text = DecimalFormat("#.##").format(bmi)
            bmiTV.setTextColor(getBmiColor(bmi))
        }
    }

    private fun getDataFromTextFields() {
        binding.apply {
            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
                return
            }

            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
                return
            }

            mass = massET.text.toString().toDouble()
            height = heightET.text.toString().toDouble()
        }
    }

    private fun updateBmiFormatTexts() {
        binding.apply {
            heightTV.text = BmiUtils.getHeightString(format)
            massTV.text = BmiUtils.getMassString(format)
        }
    }

    fun changeBmiFormat(item: MenuItem) {
        format = when(format) {
            BmiFormat.EUROPEAN -> BmiFormat.AMERICAN
            BmiFormat.AMERICAN -> BmiFormat.EUROPEAN
        }
        updateBmiFormatTexts()
        updateBmiText()
    }

    fun showBmiDetails(view: View) {
        val intent = Intent(this, BmiDetailsActivity::class.java).apply {
            putExtra(BMI_KEY, bmi)
        }
        startActivityForResult(intent, 0)
    }

    fun showRecords(item: MenuItem) {
        val intent = Intent(this, BmiHistoryActivity::class.java)
        startActivity(intent)
    }
}
