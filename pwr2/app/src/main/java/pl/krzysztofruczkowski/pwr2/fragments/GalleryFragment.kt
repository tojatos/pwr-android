package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.adapters.GalleryAdapter
import pl.krzysztofruczkowski.pwr2.databinding.FragmentGalleryBinding
import pl.krzysztofruczkowski.pwr2.getImageIdByName
import pl.krzysztofruczkowski.pwr2.viewmodels.MainViewModel
import kotlin.random.Random

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentGalleryBinding>(inflater, R.layout.fragment_gallery, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val pokemon = viewModel.selectedPokemon.value!!

        val random = Random(pokemon.name.hashCode())
        val numberOfImages = random.nextInt(1, 5)
        val ids = (1..5).shuffled(random).take(numberOfImages)
        val imageIds = ids.map { "gallery_$it" }.map { fileName ->
            getImageIdByName(fileName, requireActivity())
        }

        binding.apply {
            rvGallery.adapter = GalleryAdapter(imageIds)
        }

        return binding.root
    }

}