package pl.krzysztofruczkowski.pwr1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.krzysztofruczkowski.pwr1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //TODO oprogramowac zapamietywanie stanu ui (tam gdzie potrzeba)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //TODO odt. stanu
    }

    fun count(view: View) {
        binding.apply {
            //TODO oprogramowac liczenie bmi i sprawdzanie danych wejsciowych
            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
            }
            bmiTV.text = heightET.text.toString()
        }
    }
}
