package pl.krzysztofruczkowski.pwr1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofruczkowski.pwr1.databinding.ActivityBmiDetailsBinding

class BmiDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiDetailsBinding

    private fun getBmiDescription(bmi : Double) : String = when {
        bmi < 16 -> "You are starving!\nEat something right now!"
        bmi < 16.99 -> "You could eat something..."
        bmi < 18.49 -> "You look slim.\nBut you are fine."
        bmi < 24.99 -> "Perfect body mass!"
        bmi < 29.99 -> "You are a bit fat.\nRun a bit and you will be fine."
        bmi < 34.99 -> "You are a bit obese."
        bmi < 39.99 -> "You are REALLY obese."
        else -> "STOP EATING MCDONALDS OR YOU WILL DIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            val bmi = intent.getDoubleExtra(MainActivity.BMI_KEY, 0.0)
            bmiTV.text = bmi.toString()
            descriptionTV.text = getBmiDescription(bmi)
        }
    }
}