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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //TODO oprogramowac zapamietywanie stanu ui (tam gdzie potrzeba)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //TODO odt. stanu
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
        updateBmi()
    }

    fun updateBmi() {
        binding.apply {

            var error = false

            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
                error = true
            }

            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
                error = true
            }

            if(error) return

            val mass = massET.text.toString().toDouble()
            val height = heightET.text.toString().toDouble()

            val resultBmi = if (europeanFormat) BmiForCmKg(mass, height).count() else BmiForInLb(mass, height).count()

            bmiTV.text = resultBmi.toString()
            bmiTV.setTextColor(getBmiColor(resultBmi))
        }
    }

    fun changeBmiFormat(item: MenuItem) {
        europeanFormat = !europeanFormat
        binding.apply {
            if(europeanFormat) {
                heightTV.text = getString(R.string.height_cm)
                massTV.text = getString(R.string.mass_kg)
            } else {
                heightTV.text = getString(R.string.height_in)
                massTV.text = getString(R.string.mass_lb)
            }
        }
        updateBmi()
    }
}
