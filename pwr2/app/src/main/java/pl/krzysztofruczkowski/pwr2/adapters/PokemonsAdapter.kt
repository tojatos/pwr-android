package pl.krzysztofruczkowski.pwr2.adapters

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.ItemPokemonBinding
import pl.krzysztofruczkowski.pwr2.getImageIdByName
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon
import java.util.*

class PokemonsAdapter(
    private val pokemons: List<Pokemon>,
    private val activity: Activity,
    val onFaviconClick: (Int) -> Unit,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val pokemon = pokemons[position]
            val imageId = getImageIdByName(pokemon.name.toLowerCase(Locale.ROOT), activity)
            binding.apply {
                itemPokemon.setOnClickListener { onItemClick(position) }
                itemPokemonName.text = pokemon.name
                itemPokemonCategory.text = pokemon.category.toString()
                itemPokemonImage.setImageResource(imageId)
                itemPokemonCategoryColor.setBackgroundColor(getColorByPokeCategory(pokemon.category))
                itemPokemonFavicon.isChecked = pokemon.favourite
                itemPokemonFavicon.setOnClickListener { onFaviconClick(position) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    private fun getColorByPokeCategory(c: PokeCategory) : Int {
        val colorString = when (c) {
            PokeCategory.None -> "#ffffff"
            PokeCategory.Lightning -> "#ffff00"
            PokeCategory.Seed -> "#35ff4f"
            PokeCategory.Mouse -> "#bbbbbb"
            PokeCategory.Flame -> "#ff5555"
        }
        return Color.parseColor(colorString)
    }
}