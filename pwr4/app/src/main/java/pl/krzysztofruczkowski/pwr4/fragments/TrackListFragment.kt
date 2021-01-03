package pl.krzysztofruczkowski.pwr4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.krzysztofruczkowski.pwr4.R
import pl.krzysztofruczkowski.pwr4.adapters.TrackAdapter
import pl.krzysztofruczkowski.pwr4.databinding.FragmentTrackListBinding
import pl.krzysztofruczkowski.pwr4.models.Track

class TrackListFragment : Fragment() {
    private lateinit var binding: FragmentTrackListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_list, container, false)

        val selectAndNavigate = { id: Int ->
//            viewModel.selectPokemon(id)
//            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
        }

//        val reloadAdapter = {
//            binding.rvPokemons.adapter = PokemonsAdapter(viewModel.filteredPokemons, requireActivity(), viewModel::onPokemonFavourite, selectAndNavigate)
//        }
        val tracks = listOf(
            Track("Name1"),
            Track("a"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
            Track("Some really fancy track title that is very long (too long)"),
        )
        binding.rvMusic.adapter = TrackAdapter(tracks, selectAndNavigate)

        return binding.root
    }
}