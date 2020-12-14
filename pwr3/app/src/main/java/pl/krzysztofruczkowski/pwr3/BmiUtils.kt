package pl.krzysztofruczkowski.pwr3

import android.graphics.Color
import pl.krzysztofruczkowski.pwr3.activities.MainActivity
import pl.krzysztofruczkowski.pwr3.models.BmiFormat

object BmiUtils {
    private fun getString(id: Int) = MainActivity.app_resources.getString(id)

    fun calculateBmi(mass: Double, height: Double, format: BmiFormat) = when(format) {
        BmiFormat.EUROPEAN -> BmiForCmKg(mass, height).count()
        BmiFormat.AMERICAN -> BmiForInLb(mass, height).count()
    }

    fun getHeightUnit(format: BmiFormat) = when (format) {
        BmiFormat.EUROPEAN -> getString(R.string.height_unit_cm)
        BmiFormat.AMERICAN -> getString(R.string.height_unit_in)
    }
    fun getMassUnit(format: BmiFormat) = when (format) {
        BmiFormat.EUROPEAN -> getString(R.string.mass_unit_kg)
        BmiFormat.AMERICAN -> getString(R.string.mass_unit_lb)
    }

    fun getHeightString(format: BmiFormat) = when(format) {
        BmiFormat.EUROPEAN -> getString(R.string.height_cm)
        BmiFormat.AMERICAN -> getString(R.string.height_in)
    }

    fun getMassString(format: BmiFormat) = when(format) {
        BmiFormat.EUROPEAN -> getString(R.string.mass_kg)
        BmiFormat.AMERICAN -> getString(R.string.mass_lb)
    }

    fun getBmiColor(bmi: Double) : Int {
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


    fun getBmiDescription(bmi : Double) : String = when {
        bmi < 16 -> "You are starving!\nEat something right now!"
        bmi < 16.99 -> "You could eat something..."
        bmi < 18.49 -> "You look slim.\nBut you are fine."
        bmi < 24.99 -> "Perfect body mass!"
        bmi < 29.99 -> "You are a bit fat.\nRun a bit and you will be fine."
        bmi < 34.99 -> "You are a bit obese."
        bmi < 39.99 -> "You are REALLY obese."
        else -> "STOP EATING MCDONALDS OR YOU WILL DIE"
    }

}
