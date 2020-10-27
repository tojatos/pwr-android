package pl.krzysztofruczkowski.pwr1.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr1.BmiForCmKg
import pl.krzysztofruczkowski.pwr1.BmiForInLb
import pl.krzysztofruczkowski.pwr1.R
import pl.krzysztofruczkowski.pwr1.databinding.ActivityMainBinding
import pl.krzysztofruczkowski.pwr1.models.Record
import java.text.DecimalFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    companion object {
        const val EUROPEAN_FORMAT_KEY = "EUROPEAN_FORMAT"
        const val HEIGHT_KEY = "HEIGHT"
        const val MASS_KEY = "MASS"
        const val BMI_KEY = "BMI"
    }
    private lateinit var binding: ActivityMainBinding
    private var europeanFormat = true
    private var height: Double = 0.0
    private var mass: Double = 0.0
    private var bmi: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean(EUROPEAN_FORMAT_KEY, europeanFormat)
            putDouble(HEIGHT_KEY, height)
            putDouble(MASS_KEY, mass)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        europeanFormat = savedInstanceState.getBoolean(EUROPEAN_FORMAT_KEY)
        height = savedInstanceState.getDouble(HEIGHT_KEY)
        mass = savedInstanceState.getDouble(MASS_KEY)

        updateBmiFormat()
        updateBmi()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun getBmiColor(bmi : Double) : Int {
        val colorString = when {
            bmi < 16 -> "#082E79"
            bmi < 16.99 -> "#4169E1"
            bmi < 18.49 -> "#ACE1AF"
            bmi < 24.99 -> "#CDEBA7"
            bmi < 29.99 -> "#FFFF99"
            bmi < 34.99 -> "#FDE456"
            bmi < 39.99 -> "#CF2929"
            else -> "#801818"
        }
        return Color.parseColor(colorString)
    }

    fun count(view: View) {
        getDataFromTextFields()
        updateBmi()
    }

    private fun updateBmi() {
        binding.apply {
            bmi = if (europeanFormat) BmiForCmKg(mass, height).count() else BmiForInLb(mass, height).count()

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

    private fun updateBmiFormat() {
        binding.apply {
            if(europeanFormat) {
                heightTV.text = getString(R.string.height_cm)
                massTV.text = getString(R.string.mass_kg)
            } else {
                heightTV.text = getString(R.string.height_in)
                massTV.text = getString(R.string.mass_lb)
            }
        }
    }

    fun changeBmiFormat(item: MenuItem) {
        europeanFormat = !europeanFormat
        updateBmiFormat()
        updateBmi()
    }

    fun showBmiDetails(view: View) {
        saveBmiRecord(bmi)

        val intent = Intent(this, BmiDetailsActivity::class.java).apply {
            putExtra(BMI_KEY, bmi)
        }
        startActivityForResult(intent, 0)
    }

    private fun saveBmiRecord(bmi: Double) {
        val sharedPref = getSharedPreferences(getString(R.string.records_file_key), Context.MODE_PRIVATE) ?: return
        val recordsString = sharedPref.getString(getString(R.string.saved_records_key), "[]").toString()
        val records = Json.decodeFromString<List<Record>>(recordsString)
        val newRecord = Record(bmi, Date().toString())
        val newRecordsList = (if (records.size < 10) records else records.drop(1)) + newRecord
        with (sharedPref.edit()) {
            putString(getString(R.string.saved_records_key), Json.encodeToString(newRecordsList))
            apply()
        }
    }

    fun showRecords(item: MenuItem) {
        val intent = Intent(this, BmiHistoryActivity::class.java)
        startActivity(intent)
    }
}
