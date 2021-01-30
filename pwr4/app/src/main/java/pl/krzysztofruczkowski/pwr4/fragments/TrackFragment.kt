package pl.krzysztofruczkowski.pwr4.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.krzysztofruczkowski.pwr4.MainViewModel
import pl.krzysztofruczkowski.pwr4.R
import pl.krzysztofruczkowski.pwr4.adapters.TrackAdapter
import pl.krzysztofruczkowski.pwr4.databinding.FragmentTrackBinding
import pl.krzysztofruczkowski.pwr4.databinding.FragmentTrackListBinding
import pl.krzysztofruczkowski.pwr4.models.Track

class TrackFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val mp = MediaPlayer()
//        mp.setOnPreparedListener {
//            mp.start()
//        }
        val binding = DataBindingUtil.inflate<FragmentTrackBinding>(inflater, R.layout.fragment_track, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val selectedTrack = viewModel.selectedTrack.value!!
        requireActivity().mediaController.transportControls.playFromMediaId(selectedTrack.metadata.description.mediaId, null)

//        mp.setDataSource(requireContext(), selectedTrack.uri)
//        mp.prepareAsync()
//
//        var paused = false
//        mp.pause()

        binding.apply {
            songNameTV.text = selectedTrack.name
            pausePlayButton.setOnClickListener {
//                if (paused) {
//                    mp.start()
//                } else {
//                    mp.pause()
//                }
//                paused = !paused
            }
//            seekBar.max = mp.duration / 1000
//            seekBar.setOnSeekBarChangeListener {
//                mp.seekTo(seekBar.progress)
//            }

        }

        return binding.root
    }
}