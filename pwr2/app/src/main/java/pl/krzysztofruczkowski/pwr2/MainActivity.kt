package pl.krzysztofruczkowski.pwr2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.krzysztofruczkowski.pwr2.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}