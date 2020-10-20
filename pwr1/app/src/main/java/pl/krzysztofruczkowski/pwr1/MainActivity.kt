package pl.krzysztofruczkowski.pwr1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import pl.krzysztofruczkowski.pwr1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var europeanFormat = true
    private var height: Double = 0.0
    private var mass: Double = 0.0

    private val EUROPEAN_FORMAT_KEY = "EUROPEAN_FORMAT"
    private val HEIGHT_KEY = "HEIGHT"
    private val MASS_KEY = "MASS"

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
            val resultBmi = if (europeanFormat) BmiForCmKg(mass, height).count() else BmiForInLb(mass, height).count()

            bmiTV.text = resultBmi.toString()
            bmiTV.setTextColor(getBmiColor(resultBmi))
        }
    }
    private fun getDataFromTextFields() {
        binding.apply {
            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
            }

            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
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
}
