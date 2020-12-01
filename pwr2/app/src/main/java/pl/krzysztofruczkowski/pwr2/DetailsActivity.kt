package pl.krzysztofruczkowski.pwr2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pl.krzysztofruczkowski.pwr2.databinding.DetailsActivityBinding
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class DetailsActivity : AppCompatActivity() {

    lateinit var pokemon: Pokemon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = Pokemon.deserialize(intent.getStringExtra(MainActivity.POKEMON)!!)

        val binding = DataBindingUtil.setContentView<DetailsActivityBinding>(this, R.layout.details_activity)
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.page_1 -> {
//                    findNavController().navigate(R.id.action_descriptionFragment_to_enemiesFragment)
//                    true
//                }
//                R.id.page_2 -> {
//                    findNavController().navigate(R.id.action_enemiesFragment_to_descriptionFragment)
//                    true
//                }
//                else -> false
//            }
//        }
        val navController = Navigation.findNavController(this, R.id.detailsNavHostFragment)

        binding.apply {
            bottomNavigation.setupWithNavController(navController)
        }
    }
}