package pl.krzysztofruczkowski.pwr2

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.databinding.MainActivityBinding
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var app_resources: Resources
        lateinit var app_package_name: String
    }

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    private val removeItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) =
            false
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
            viewModel.onPokemonSwipe(viewHolder.adapterPosition)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app_resources = resources
        app_package_name = packageName

        binding = MainActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.rvPokemons.adapter = PokemonsAdapter(viewModel.pokemons.value ?: emptyList(), viewModel::onPokemonFavourite)

        viewModel.pokemons.observe(this, {
            binding.rvPokemons.adapter = PokemonsAdapter(it, viewModel::onPokemonFavourite)
        })

        removeItemTouchHelper.attachToRecyclerView(binding.rvPokemons)
        setContentView(binding.root)
    }
}