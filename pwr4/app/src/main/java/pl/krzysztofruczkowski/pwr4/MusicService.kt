package pl.krzysztofruczkowski.pwr4

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service(),
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener,
    AudioManager.OnAudioFocusChangeListener {

    private lateinit var audioManager: AudioManager

    override fun onCreate() {
        super.onCreate()

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onAudioFocusChange(focusChange: Int) {
        TODO("Not yet implemented")
    }

}