package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_description.*
import kotlinx.android.synthetic.main.fragment_enemies.*
import pl.krzysztofruczkowski.pwr2.*
import pl.krzysztofruczkowski.pwr2.databinding.FragmentDescriptionBinding
import pl.krzysztofruczkowski.pwr2.databinding.FragmentEnemiesBinding
import java.util.*

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