package pl.krzysztofruczkowski.pwr2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofruczkowski.pwr2.databinding.MainActivityBinding
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private val pokemons = listOf(
        Pokemon("Jolteon", "swift"),
        Pokemon("Bulbazaur", "blue"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        binding.rvPokemons.adapter = PokemonsAdapter(pokemons)
        setContentView(binding.root)
    }
}