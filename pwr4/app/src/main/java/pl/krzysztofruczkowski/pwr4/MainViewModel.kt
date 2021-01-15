package pl.krzysztofruczkowski.pwr4

import android.app.Application
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

    fun updateTracks(tracks: List<Track>) {
        _tracks.value = tracks as ArrayList<Track>
    }

    fun selectTrack(position: Int) {
        _selectedTrack.value = _tracks.value!![position]
    }

    init {
        _tracks.value = arrayListOf()
    }
}