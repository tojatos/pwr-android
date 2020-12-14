package pl.krzysztofruczkowski.pwr3.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofruczkowski.pwr3.BmiUtils.getBmiDescription
import pl.krzysztofruczkowski.pwr3.databinding.ActivityBmiDetailsBinding
import java.text.DecimalFormat

class BmiDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            val bmi = intent.getDoubleExtra(MainActivity.BMI_KEY, 0.0)
            bmiTV.text = DecimalFormat("#.##").format(bmi)
            descriptionTV.text = getBmiDescription(bmi)
        }
    }
}
