package pl.krzysztofruczkowski.pwr4

import android.app.Notification
import android.app.Notification.MediaStyle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

class MediaNotificationManager(private val mService: MusicService) : BroadcastReceiver() {
    private val mNotificationManager: NotificationManager
    private val mPlayAction: Notification.Action
    private val mPauseAction: Notification.Action
    private val mNextAction: Notification.Action
    private val mPrevAction: Notification.Action
    private var mStarted = false
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PAUSE -> mService.mCallback.onPause()
            ACTION_PLAY -> mService.mCallback.onPlay()
            ACTION_NEXT -> mService.mCallback.onSkipToNext()
            ACTION_PREV -> mService.mCallback.onSkipToPrevious()
        }
    }

    fun update(metadata: MediaMetadata?, state: PlaybackState?, token: MediaSession.Token?) {
        if (state == null || state.state == PlaybackState.STATE_STOPPED || state.state == PlaybackState.STATE_NONE) {
            mService.stopForeground(true)
            try {
                mService.unregisterReceiver(this)
            } catch (ex: IllegalArgumentException) {
                // ignore receiver not registered
            }
            mService.stopSelf()
            return
        }
        if (metadata == null) {
            return
        }
        val isPlaying = state.state == PlaybackState.STATE_PLAYING
        val channelId = createNotificationChannel("my_service", "My Background Service")
        val notificationBuilder = Notification.Builder(mService, channelId)
        val description = metadata.description
        notificationBuilder
                .setStyle(MediaStyle()
                        .setMediaSession(token)
                        .setShowActionsInCompactView(0, 1, 2))
                .setColor(Color.BLACK)
                .setSmallIcon(R.drawable.ic_baseline_audiotrack_24)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentIntent(createContentIntent())
                .setContentTitle(description.title)
                .setContentText(description.subtitle)
//                .setLargeIcon(MusicLibrary.getAlbumBitmap(mService, description.mediaId))
                .setOngoing(isPlaying)
                .setWhen(if (isPlaying) System.currentTimeMillis() - state.position else 0)
                .setShowWhen(isPlaying)
                .setUsesChronometer(isPlaying)

        // If skip to next action is enabled
        if (state.actions and PlaybackState.ACTION_SKIP_TO_PREVIOUS != 0L) {
            notificationBuilder.addAction(mPrevAction)
        }
        notificationBuilder.addAction(if (isPlaying) mPauseAction else mPlayAction)

        // If skip to prev action is enabled
        if (state.actions and PlaybackState.ACTION_SKIP_TO_NEXT != 0L) {
            notificationBuilder.addAction(mNextAction)
        }
        val notification = notificationBuilder.build()
        if (isPlaying && !mStarted) {
            mService.startService(Intent(mService.applicationContext, MusicService::class.java))
            mService.startForeground(NOTIFICATION_ID, notification)
            mStarted = true
        } else {
            if (!isPlaying) {
                mService.stopForeground(false)
                mStarted = false
            }
            mNotificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createContentIntent(): PendingIntent {
        val openUI = Intent(mService, MainActivity::class.java)
        openUI.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(mService, REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT)
    }

    companion object {
        private const val NOTIFICATION_ID = 412
        private const val REQUEST_CODE = 100
        private const val ACTION_PAUSE = "pl.krzysztofruczkowski.pwr4.pause"
        private const val ACTION_PLAY = "pl.krzysztofruczkowski.pwr4.play"
        private const val ACTION_NEXT = "pl.krzysztofruczkowski.pwr4.next"
        private const val ACTION_PREV = "pl.krzysztofruczkowski.pwr4.prev"
    }

    init {
        val pkg = mService.packageName
        val playIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        val pauseIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        val nextIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        val prevIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPlayAction = Notification.Action(R.drawable.ic_play_arrow_white_24dp,
                mService.getString(R.string.label_play), playIntent)
        mPauseAction = Notification.Action(R.drawable.ic_pause_white_24dp,
                mService.getString(R.string.label_pause), pauseIntent)
        mNextAction = Notification.Action(R.drawable.ic_skip_next_white_24dp,
                mService.getString(R.string.label_next), nextIntent)
        mPrevAction = Notification.Action(R.drawable.ic_skip_previous_white_24dp,
                mService.getString(R.string.label_previous), prevIntent)
        val filter = IntentFilter()
        filter.addAction(ACTION_NEXT)
        filter.addAction(ACTION_PAUSE)
        filter.addAction(ACTION_PLAY)
        filter.addAction(ACTION_PREV)
        mService.registerReceiver(this, filter)
        mNotificationManager = mService
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        mNotificationManager.cancelAll()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = mService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}