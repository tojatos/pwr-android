package pl.krzysztofruczkowski.pwr1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import pl.krzysztofruczkowski.pwr1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    fun getBmiColor(bmi : Double) : Int {
        val colorString = when {
            bmi < 16 -> "#082E79"
            bmi < 16.99 -> "#4169E1"
            bmi < 18.49 -> "##ACE1AF"
            bmi < 24.99 -> "#CDEBA7"
            bmi < 29.99 -> "#FFFF99"
            bmi < 34.99 -> "#FDE456"
            bmi < 39.99 -> "#CF2929"
            else -> "#801818"
        }
        return Color.parseColor(colorString)
    }

    fun count(view: View) {
        binding.apply {
            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
                return
            }

            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
                return
            }

            val mass = massET.text.toString().toDouble()
            val height = heightET.text.toString().toDouble()

            val resultBmi = BmiForCmKg(mass, height).count()

            bmiTV.text = resultBmi.toString()
            bmiTV.setTextColor(getBmiColor(resultBmi))
        }
    }
}
