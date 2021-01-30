package pl.krzysztofruczkowski.pwr4.models

import android.media.MediaMetadata
import android.net.Uri

data class Track(val name: String, val uri: Uri, val metadata: MediaMetadata)
