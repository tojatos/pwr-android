package pl.krzysztofruczkowski.pwr4.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pl.krzysztofruczkowski.pwr4.MainViewModel
import pl.krzysztofruczkowski.pwr4.R
import pl.krzysztofruczkowski.pwr4.adapters.TrackAdapter
import pl.krzysztofruczkowski.pwr4.databinding.FragmentTrackListBinding
import pl.krzysztofruczkowski.pwr4.models.Track

class TrackListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTrackListBinding>(inflater, R.layout.fragment_track_list, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val selectAndNavigate = { id: Int ->
            viewModel.selectTrack(id)
            findNavController().navigate(R.id.action_mainFragment_to_trackFragment)
            requireActivity().mediaController.transportControls.playFromMediaId(viewModel.selectedTrack.value!!.metadata.description.mediaId, null)
        }


        val reloadAdapter = {
            val tracks: List<Track> = viewModel.tracks.value as List<Track>
            binding.rvMusic.adapter = TrackAdapter(tracks, selectAndNavigate)
            if (tracks.isEmpty()) {
                binding.emptyMusicText.visibility = View.VISIBLE
                binding.rvMusic.visibility = View.GONE
            } else {
                binding.emptyMusicText.visibility = View.GONE
                binding.rvMusic.visibility = View.VISIBLE
            }
        }

        viewModel.tracks.observe(viewLifecycleOwner, { reloadAdapter() })

        return binding.root
    }
}