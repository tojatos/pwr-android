package pl.krzysztofruczkowski.pwr4

import android.media.browse.MediaBrowser.MediaItem
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.service.media.MediaBrowserService

class MusicService : MediaBrowserService() {
    private var mSession: MediaSession? = null
    private lateinit var stateBuilder: PlaybackState.Builder
    private lateinit var mPlayback: PlaybackManager
    val mCallback: MediaSession.Callback = object : MediaSession.Callback() {
        override fun onPlayFromMediaId(mediaId: String, extras: Bundle?) {
            mSession!!.isActive = true
            val track = MusicLibrary.tracks.find { it.metadata.description.mediaId == mediaId }!!
            mSession!!.setMetadata(track.metadata)
            mPlayback.play(track.metadata)
        }

        override fun onPlay() {
            if (mPlayback.currentMediaId != null) {
                onPlayFromMediaId(mPlayback.currentMediaId!!, null)
            }
        }

        override fun onPause() {
            mPlayback.pause()
        }

        override fun onStop() {
            mPlayback.stop()
//            stopSelf()
        }

        override fun onSkipToNext() {
            onPlayFromMediaId(MusicLibrary.getNextSong(mPlayback.currentMediaId!!), null)
        }

        override fun onSkipToPrevious() {
            onPlayFromMediaId(MusicLibrary.getPreviousSong(mPlayback.currentMediaId!!), null)
        }

        override fun onSeekTo(pos: Long) {
            mPlayback.seekTo(pos)
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Start a new MediaSession
        mSession = MediaSession(baseContext, "MusicService").apply {
            setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS)
            setSessionToken(sessionToken)
            setCallback(mCallback)
//            stateBuilder = PlaybackState.Builder().setActions(PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PLAY_PAUSE)
//            setPlaybackState(stateBuilder.build())
        }
        val mediaNotificationManager = MediaNotificationManager(this)
        mPlayback = PlaybackManager(this, object : PlaybackManager.Callback {
            override fun onPlaybackStatusChanged(state: PlaybackState?) {
                mSession!!.setPlaybackState(state)
                mediaNotificationManager.update(applicationContext, mPlayback.currentMedia, state, sessionToken)
            }
        })
    }

    override fun onDestroy() {
        mPlayback.stop()
        mSession!!.release()
    }

    //TODO
    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
//        return BrowserRoot(MusicLibrary.getRoot(), null)
        return BrowserRoot("", null)
    }

    //TODO
    override fun onLoadChildren(parentMediaId: String, result: Result<List<MediaItem>>) {
//        result.sendResult(MusicLibrary.getMediaItems())
    }
}
