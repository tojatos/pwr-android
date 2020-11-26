package pl.krzysztofruczkowski.pwr2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon
import java.util.*

class PokemonsAdapter(private val pokemons: List<Pokemon>) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val pokemonItem: LinearLayout = itemView.findViewById(R.id.item_pokemon)
        val pokemonNameTV: TextView = itemView.findViewById(R.id.item_pokemon_name)
        val pokemonCategoryTV: TextView = itemView.findViewById(R.id.item_pokemon_category)
        val pokemonImage: ImageView = itemView.findViewById(R.id.item_pokemon_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon: Pokemon = pokemons[position]

        holder.pokemonNameTV.text = pokemon.name
        holder.pokemonCategoryTV.text = pokemon.category.toString()
        val imageId = MainActivity.app_resources.getIdentifier(pokemon.name.toLowerCase( Locale.ROOT), "drawable", MainActivity.app_package_name)
        holder.pokemonImage.setBackgroundResource(imageId)
        holder.pokemonItem.setBackgroundColor(getColorByPokeCategory(pokemon.category))
    }

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