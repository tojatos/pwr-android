package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.krzysztofruczkowski.pwr2.DetailsActivity
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.databinding.FragmentDescriptionBinding
import pl.krzysztofruczkowski.pwr2.getImageIdByName
import java.util.*

class DescriptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentDescriptionBinding>(inflater, R.layout.fragment_description, container, false)
        val pokemon = (activity as DetailsActivity).pokemon

        binding.apply {
            pokemonName.text = pokemon.name
            val imageId = getImageIdByName(pokemon.name.toLowerCase(Locale.ROOT), (activity as DetailsActivity).resources, (activity as DetailsActivity).packageName)
            pokemonImage.setImageResource(imageId)
            description.text = pokemon.description
        }

        return binding.root
    }
}