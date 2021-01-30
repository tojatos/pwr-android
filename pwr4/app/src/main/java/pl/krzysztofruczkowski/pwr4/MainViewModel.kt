package pl.krzysztofruczkowski.pwr4

import android.app.Application
import android.media.session.PlaybackState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.krzysztofruczkowski.pwr4.models.Track

class MainViewModel(application: Application) :AndroidViewModel(application) {
    private val _tracks = MutableLiveData<ArrayList<Track>>()
    val tracks: LiveData<ArrayList<Track>>
        get() = _tracks

    private val _selectedTrack = MutableLiveData<Track>()
    val selectedTrack: LiveData<Track>
        get() = _selectedTrack

    private val _playbackState = MutableLiveData<PlaybackState?>()
    val playbackState: LiveData<PlaybackState?>
        get() = _playbackState

    fun updateTracks(tracks: List<Track>) {
        _tracks.value = tracks as ArrayList<Track>
        MusicLibrary.tracks = tracks
    }

    fun selectTrack(position: Int) {
        _selectedTrack.value = _tracks.value!![position]
    }

    fun selectTrack(track: Track) {
        _selectedTrack.value = track
    }

    fun updatePlaybackState(state: PlaybackState?) {
        _playbackState.value = state
    }

    init {
        _tracks.value = arrayListOf()
    }
}