package pl.krzysztofruczkowski.pwr2

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.databinding.MainActivityBinding
import pl.krzysztofruczkowski.pwr2.models.PokeCategory

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

        binding.categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, PokeCategory.values())
        binding.categorySpinner.onItemSelectedListener = this

        val reloadAdapter = {
            binding.rvPokemons.adapter = PokemonsAdapter(viewModel.filteredPokemons ?: emptyList(), viewModel::onPokemonFavourite)
        }
        reloadAdapter()

        viewModel.pokemons.observe(this, {
            reloadAdapter()
//            binding.rvPokemons.adapter = PokemonsAdapter(viewModel.filteredPokemons ?: emptyList(), viewModel::onPokemonFavourite)
        })

        viewModel.selectedCategory.observe(this, {
            reloadAdapter()
//            binding.rvPokemons.adapter = PokemonsAdapter(it, viewModel::onPokemonFavourite)
        })

        removeItemTouchHelper.attachToRecyclerView(binding.rvPokemons)
        setContentView(binding.root)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onCategorySelect(PokeCategory.values().get(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        viewModel.onCategorySelect(PokeCategory.None)
    }
}