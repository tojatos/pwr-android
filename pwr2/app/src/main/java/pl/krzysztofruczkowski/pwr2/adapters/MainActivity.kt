package pl.krzysztofruczkowski.pwr2.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import pl.krzysztofruczkowski.pwr2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(R.layout.main_activity)
    }
}