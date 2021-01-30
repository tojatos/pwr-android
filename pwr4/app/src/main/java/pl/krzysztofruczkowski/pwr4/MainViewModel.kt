package pl.krzysztofruczkowski.pwr4

import android.app.Application
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.krzysztofruczkowski.pwr4.models.Track
import java.lang.Exception

class MainViewModel(application: Application) :AndroidViewModel(application) {
    private val _tracks = MutableLiveData<ArrayList<Track>>()
    val tracks: LiveData<ArrayList<Track>>
        get() = _tracks

    private val _selectedTrack = MutableLiveData<Track>()
    val selectedTrack: LiveData<Track>
        get() = _selectedTrack

    fun updateTracks(tracks: List<Track>) {
        _tracks.value = tracks as ArrayList<Track>
        MusicLibrary.tracks = tracks
//        Log.e("TAG", _tracks.value.toString())

//        tracks.toList().forEach {
//            try {
//                val r = MediaMetadataRetriever()
//                r.setDataSource(getApplication(), it.uri)
//                val metadata = r.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
//                Log.e(it.name, metadata.orEmpty())
//            } catch (e: Exception) {
//                Log.e("EXCEPT", e.message.orEmpty())
//            }
//        }

    }

    fun selectTrack(position: Int) {
        _selectedTrack.value = _tracks.value!![position]
    }

    init {
        _tracks.value = arrayListOf()
    }
}