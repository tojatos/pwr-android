package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.adapters.PokemonsAdapter
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.FragmentMainBinding
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.viewmodels.MainViewModel

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    private val removeItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) =
            false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
            viewModel.onPokemonSwipe(viewHolder.adapterPosition)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.categorySpinner.adapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, PokeCategory.values())
        binding.categorySpinner.onItemSelectedListener = this

        viewModel.filterByFavourite.observe(viewLifecycleOwner, {
            binding.favouriteFilter.isChecked = it
        })
        binding.favouriteFilter.setOnClickListener { viewModel.onFavouriteFilterClick() }

        val selectAndNavigate = { id: Int ->
            viewModel.selectPokemon(id)
            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
        }

        val reloadAdapter = {
            binding.rvPokemons.adapter = PokemonsAdapter(viewModel.filteredPokemons, requireActivity(), viewModel::onPokemonFavourite, selectAndNavigate)
        }

        viewModel.pokemons.observe(viewLifecycleOwner, { reloadAdapter() })
        viewModel.selectedCategory.observe(viewLifecycleOwner, { reloadAdapter() })
        viewModel.filterByFavourite.observe(viewLifecycleOwner, { reloadAdapter() })

        removeItemTouchHelper.attachToRecyclerView(binding.rvPokemons)
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onCategorySelect(PokeCategory.values()[position])
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        viewModel.onCategorySelect(PokeCategory.None)
    }
}