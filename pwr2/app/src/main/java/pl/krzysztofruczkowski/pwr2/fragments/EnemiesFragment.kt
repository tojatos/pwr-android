package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.krzysztofruczkowski.pwr2.DetailsActivity
import pl.krzysztofruczkowski.pwr2.EnemiesAdapter
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.FragmentEnemiesBinding

class EnemiesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentEnemiesBinding>(inflater, R.layout.fragment_enemies, container, false)
        val pokemon = (activity as DetailsActivity).pokemon

        binding.apply {
            rvEnemies.adapter = EnemiesAdapter(pokemon.enemies)
        }

        return binding.root
    }

}