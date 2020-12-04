package pl.krzysztofruczkowski.pwr2

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.databinding.MainActivityBinding
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        lateinit var app_resources: Resources
        lateinit var app_package_name: String
        const val POKEMON = "pl.krzysztofruczkowski.pwr2.POKEMON"
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        app_resources = resources
        app_package_name = packageName

        binding = MainActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, PokeCategory.values())
        binding.categorySpinner.onItemSelectedListener = this

        viewModel.filterByFavourite.observe(this, {
            binding.favouriteFilter.isChecked = it
        })
        binding.favouriteFilter.setOnClickListener { viewModel.onFavouriteFilterClick() }


        val loadDescription = { pokemon: Pokemon ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(POKEMON, Pokemon.serialize(pokemon))
            }
            startActivity(intent)
        }

        val loadDescriptionById = { id: Int ->
            loadDescription(viewModel.filteredPokemons[id])
        }

        val reloadAdapter = {
            binding.rvPokemons.adapter = PokemonsAdapter(viewModel.filteredPokemons, viewModel::onPokemonFavourite, loadDescriptionById)
        }

        viewModel.pokemons.observe(this, { reloadAdapter() })
        viewModel.selectedCategory.observe(this, { reloadAdapter() })
        viewModel.filterByFavourite.observe(this, { reloadAdapter() })

        removeItemTouchHelper.attachToRecyclerView(binding.rvPokemons)

        setContentView(binding.root)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onCategorySelect(PokeCategory.values()[position])
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        viewModel.onCategorySelect(PokeCategory.None)
    }
}