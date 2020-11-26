package pl.krzysztofruczkowski.pwr2

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofruczkowski.pwr2.databinding.MainActivityBinding
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var app_resources: Resources
        lateinit var app_package_name: String
    }
    private lateinit var binding: MainActivityBinding
    private val pokemons = listOf(
        Pokemon("Jolteon", "If it is angered or startled, the fur all over its body bristles like sharp needles that pierce foes. ", PokeCategory.Lightning),
        Pokemon("Bulbasaur", "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.", PokeCategory.Seed),
        Pokemon("Pikachu", "Pikachu that can generate powerful electricity have cheek sacs that are extra soft and super stretchy.", PokeCategory.Mouse),
        Pokemon("Charizard", "It spits fire that is hot enough to melt boulders. It may cause forest fires by blowing flames. ", PokeCategory.Flame),
        Pokemon("Raichu", "Its long tail serves as a ground to protect itself from its own high-voltage power. ", PokeCategory.Mouse),
        Pokemon("Flareon", "Once it has stored up enough heat, this Pokémon’s body temperature can reach up to 1,700 degrees Fahrenheit. ", PokeCategory.Flame),
        Pokemon("Electrike", "It stores static electricity in its fur for discharging. It gives off sparks if a storm approaches. ", PokeCategory.Lightning),
        Pokemon("Sunkern", "Sunkern tries to move as little as it possibly can. It does so because it tries to conserve all the nutrients it has stored in its body for its evolution. It will not eat a thing, subsisting only on morning dew. ", PokeCategory.Seed),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app_resources = resources
        app_package_name = packageName

        binding = MainActivityBinding.inflate(layoutInflater)
        binding.rvPokemons.adapter = PokemonsAdapter(pokemons)
        setContentView(binding.root)
    }
}