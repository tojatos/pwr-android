package pl.krzysztofruczkowski.pwr4

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.session.PlaybackState
import android.os.PowerManager
import android.os.SystemClock
import pl.krzysztofruczkowski.pwr4.models.Track
import java.io.IOException

class PlaybackManager(private val mContext: Context, callback: Callback?) : OnAudioFocusChangeListener, OnCompletionListener {
    private var mState = 0
    private var mPlayOnFocusGain = false

    var currentTrack: Track? = null
    val currentMedia: MediaMetadata?
        get() = currentTrack?.metadata
    private var mMediaPlayer: MediaPlayer? = null
    private val mCallback: Callback?
    private val mAudioManager: AudioManager
    val isPlaying: Boolean
        get() = mPlayOnFocusGain || mMediaPlayer != null && mMediaPlayer!!.isPlaying
    val currentMediaId: String?
        get() = if (currentMedia == null) null else currentMedia!!.description.mediaId
    val currentStreamPosition: Int
        get() = if (mMediaPlayer != null) mMediaPlayer!!.currentPosition else 0

    fun play(metadata: MediaMetadata) {
        val mediaId = metadata.description.mediaId
        val mediaChanged = currentMedia == null || currentMediaId != mediaId
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer!!.setWakeMode(mContext.applicationContext,
                    PowerManager.PARTIAL_WAKE_LOCK)
            mMediaPlayer!!.setOnCompletionListener(this)
        } else {
            if (mediaChanged) {
                mMediaPlayer!!.reset()
            }
        }
        if (mediaChanged) {
//            currentMedia = metadata
            currentTrack = MusicLibrary.tracks.find { it.metadata.description.mediaId == mediaId }
            try {
                mMediaPlayer!!.setDataSource(mContext.applicationContext, currentTrack!!.uri)
                mMediaPlayer!!.prepare()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        if (tryToGetAudioFocus()) {
            mPlayOnFocusGain = false
            mMediaPlayer!!.start()
            mState = PlaybackState.STATE_PLAYING
            updatePlaybackState()
        } else {
            mPlayOnFocusGain = true
        }
    }

    fun pause() {
        if (isPlaying) {
            mMediaPlayer!!.pause()
            mAudioManager.abandonAudioFocus(this)
        }
        mState = PlaybackState.STATE_PAUSED
        updatePlaybackState()
    }

    fun stop() {
        mState = PlaybackState.STATE_STOPPED
        updatePlaybackState()
//        mMediaPlayer?.stop()
        // Give up Audio focus
        mAudioManager.abandonAudioFocus(this)
        // Relax all resources
        releaseMediaPlayer()
    }

    fun seekTo(pos: Long) {
        mMediaPlayer?.seekTo(pos.toInt())
    }

    /**
     * Try to get the system audio focus.
     */
    private fun tryToGetAudioFocus(): Boolean {
        val result = mAudioManager.requestAudioFocus(
                this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    /**
     * Called by AudioManager on audio focus changes.
     * Implementation of [AudioManager.OnAudioFocusChangeListener]
     */
    override fun onAudioFocusChange(focusChange: Int) {
        var gotFullFocus = false
        var canDuck = false
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            gotFullFocus = true
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            // We have lost focus. If we can duck (low playback volume), we can keep playing.
            // Otherwise, we need to pause the playback.
            canDuck = focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
        }
        if (gotFullFocus || canDuck) {
            if (mMediaPlayer != null) {
                if (mPlayOnFocusGain) {
                    mPlayOnFocusGain = false
                    mMediaPlayer!!.start()
                    mState = PlaybackState.STATE_PLAYING
                    updatePlaybackState()
                }
                val volume = if (canDuck) 0.2f else 1.0f
                mMediaPlayer!!.setVolume(volume, volume)
            }
        } else if (mState == PlaybackState.STATE_PLAYING) {
            mMediaPlayer!!.pause()
            mState = PlaybackState.STATE_PAUSED
            updatePlaybackState()
        }
    }

    /**
     * Called when media player is done playing current song.
     *
     * @see OnCompletionListener
     */
    override fun onCompletion(player: MediaPlayer) {
        stop()
    }

    /**
     * Releases resources used by the service for playback.
     */
    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private val availableActions: Long
        private get() {
            var actions = PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackState.ACTION_PLAY_FROM_SEARCH or
                    PlaybackState.ACTION_SKIP_TO_NEXT or PlaybackState.ACTION_SKIP_TO_PREVIOUS
            if (isPlaying) {
                actions = actions or PlaybackState.ACTION_PAUSE
            }
            return actions
        }

    private fun updatePlaybackState() {
        if (mCallback == null) {
            return
        }
        val stateBuilder = PlaybackState.Builder()
                .setActions(availableActions)
        stateBuilder.setState(mState, currentStreamPosition.toLong(), 1.0f, SystemClock.elapsedRealtime())
        mCallback.onPlaybackStatusChanged(stateBuilder.build())
    }

    interface Callback {
        fun onPlaybackStatusChanged(state: PlaybackState?)
    }

    init {
        mAudioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mCallback = callback
    }
}
