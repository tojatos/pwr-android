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

    fun updateTracks(tracks: List<Track>) {
        _tracks.value = tracks as ArrayList<Track>
    }

    init {
        _tracks.value = arrayListOf(
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
    }
}