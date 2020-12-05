package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.krzysztofruczkowski.pwr2.adapters.EnemiesAdapter
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.FragmentEnemiesBinding
import pl.krzysztofruczkowski.pwr2.viewmodels.MainViewModel

class EnemiesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentEnemiesBinding>(inflater, R.layout.fragment_enemies, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val pokemon = viewModel.selectedPokemon.value!!

        binding.apply {
            rvEnemies.adapter = EnemiesAdapter(pokemon.enemies)
        }

        return binding.root
    }

}