package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_description.*
import pl.krzysztofruczkowski.pwr2.DetailsActivity
import pl.krzysztofruczkowski.pwr2.MainActivity
import pl.krzysztofruczkowski.pwr2.MainViewModel
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.FragmentDescriptionBinding
import java.util.*

class DescriptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentDescriptionBinding>(inflater, R.layout.fragment_description, container, false)
        val pokemon = (activity as DetailsActivity).pokemon

        binding.apply {
            pokemonName.text = pokemon.name
            val imageId = MainActivity.app_resources.getIdentifier(pokemon.name.toLowerCase( Locale.ROOT), "drawable", MainActivity.app_package_name)
            pokemonImage.setBackgroundResource(imageId)
            description.text = pokemon.description
        }

        return binding.root
    }

}