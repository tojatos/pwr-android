package pl.krzysztofruczkowski.pwr2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.krzysztofruczkowski.pwr2.*
import pl.krzysztofruczkowski.pwr2.databinding.FragmentGalleryBinding
import kotlin.random.Random

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<FragmentGalleryBinding>(inflater, R.layout.fragment_gallery, container, false)
        val pokemon = (activity as DetailsActivity).pokemon

        val random = Random(pokemon.name.hashCode())
        val numberOfImages = random.nextInt(1, 5)
        val ids = (1..5).shuffled(random).take(numberOfImages)
        val imageIds = ids.map { "gallery_$it" }.map { fileName ->
            getImageIdByName(fileName, MainActivity.app_resources, MainActivity.app_package_name)
        }

        binding.apply {
            rvGallery.adapter = GalleryAdapter(imageIds)
        }

        return binding.root
    }

}