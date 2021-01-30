package pl.krzysztofruczkowski.pwr4

import pl.krzysztofruczkowski.pwr4.models.Track

object MusicLibrary {
    fun getNextSong(currentMediaId: String): String {
        val currentIndex = tracks.indexOfFirst { it.metadata.description.mediaId == currentMediaId }
        var nextIndex = currentIndex + 1
        if(nextIndex >= tracks.size) nextIndex = 0
        return tracks[nextIndex].metadata.description.mediaId!!
    }

    fun getPreviousSong(currentMediaId: String): String {
        val currentIndex = tracks.indexOfFirst { it.metadata.description.mediaId == currentMediaId }
        var nextIndex = currentIndex - 1
        if(nextIndex < 0) nextIndex = tracks.size - 1
        return tracks[nextIndex].metadata.description.mediaId!!
    }

    var tracks: List<Track> = listOf()
}