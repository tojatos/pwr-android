package pl.krzysztofruczkowski.pwr2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class PokemonsAdapter(private val pokemons: List<Pokemon>) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val pokemonNameTV: TextView = itemView.findViewById(R.id.item_pokemon_name)
        val pokemonCategoryTV: TextView = itemView.findViewById(R.id.item_pokemon_category)
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
        holder.pokemonCategoryTV.text = pokemon.description
    }
}