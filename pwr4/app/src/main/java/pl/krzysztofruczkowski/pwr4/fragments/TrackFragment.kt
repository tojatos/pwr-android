package pl.krzysztofruczkowski.pwr4.fragments

import android.media.MediaMetadata
import android.media.session.PlaybackState
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.os.HandlerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pl.krzysztofruczkowski.pwr4.MainViewModel
import pl.krzysztofruczkowski.pwr4.R
import pl.krzysztofruczkowski.pwr4.databinding.FragmentTrackBinding


class TrackFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTrackBinding>(inflater, R.layout.fragment_track, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val mediaController = requireActivity().mediaController ?: return binding.root
        val transportControls = mediaController.transportControls

        val updateControls = {
            val selectedTrack = viewModel.selectedTrack.value!!
            val playbackState = viewModel.playbackState.value
            playbackState?.let {
                val notStarted = listOf(PlaybackState.STATE_PAUSED, PlaybackState.STATE_STOPPED, PlaybackState.STATE_NONE).contains(playbackState.state)
                binding.apply {
                    val pausePlayDrawable = if(notStarted) R.drawable.ic_baseline_play_arrow_24 else R.drawable.ic_baseline_pause_24
                    pausePlayButton.setImageResource(pausePlayDrawable)
                    songNameTV.text = selectedTrack.name
                    seekBar.max = selectedTrack.metadata.getLong(MediaMetadata.METADATA_KEY_DURATION).toInt()
                }
            }
        }

        binding.apply {
            pausePlayButton.setOnClickListener {
                val playbackState = viewModel.playbackState.value!!
                val notStarted = listOf(PlaybackState.STATE_PAUSED, PlaybackState.STATE_STOPPED, PlaybackState.STATE_NONE).contains(playbackState.state)
                if(notStarted) transportControls.play()
                else transportControls.pause()
            }
            stopButton.setOnClickListener {
                transportControls.stop()
//                parentFragmentManager.popBackStack()
                findNavController().popBackStack()
            }
            nextButton.setOnClickListener {
                transportControls.skipToNext()
            }
            prevButton.setOnClickListener {
                transportControls.skipToPrevious()
            }
            forward10Button.setOnClickListener {
                transportControls.pause()
                mediaController.playbackState?.let { it1 -> transportControls.seekTo(it1.position + 10 * 1000) }
                transportControls.play()
            }
            replay10Button.setOnClickListener {
                transportControls.pause()
                mediaController.playbackState?.let { it1 -> transportControls.seekTo(it1.position - 10 * 1000) }
                transportControls.play()
            }

            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if(fromUser) {
                        transportControls.pause()
                        transportControls.seekTo(progress.toLong())
                        transportControls.play()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })

        }

        viewModel.selectedTrack.observe(viewLifecycleOwner, { updateControls() })
        viewModel.playbackState.observe(viewLifecycleOwner, { updateControls() })
        val mHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        mHandler.post(object : Runnable {
            override fun run() {
                if(mediaController.playbackState != null) {
                    binding.seekBar.progress = mediaController.playbackState!!.position.toInt()
                }
                mHandler.postDelayed(this, 1000)
            }
        })


        return binding.root
    }
}