package pl.krzysztofruczkowski.pwr4

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import android.media.browse.MediaBrowser
import android.media.browse.MediaBrowser.*
import android.media.session.MediaController
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import pl.krzysztofruczkowski.pwr4.models.Track

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val SHARED_PREFS_URI_TAG = "pl.krzysztofruczkowski.pwr4.URI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mMediaBrowser = MediaBrowser(this,
                ComponentName(this, MusicService::class.java), mConnectionCallback, null)

        fun startDirectoryChooser() {
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            i.addCategory(Intent.CATEGORY_DEFAULT)
            startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999)
        }

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_directory -> {
                    startDirectoryChooser()
                    true
                }
                else -> false
            }
        }

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val treeUri = sharedPref.getString(SHARED_PREFS_URI_TAG, null)
        treeUri?.let {
            showSongs(Uri.parse(it))
        }
    }

    private lateinit var mMediaBrowser: MediaBrowser

//    private val mSubscriptionCallback: SubscriptionCallback = object : SubscriptionCallback() {
//        override fun onChildrenLoaded(parentId: String, children: List<MediaItem>) {
//            onMediaLoaded(children)
//        }
//    }

//    private fun onMediaLoaded(media: List<MediaItem>) {
//        Log.e("T", media.toString())
//        mBrowserAdapter.clear()
//        mBrowserAdapter.addAll(media)
//        mBrowserAdapter.notifyDataSetChanged()
//    }

//    private fun onMediaItemSelected(item: MediaItem) {
//        if (item.isPlayable) {
//            mediaController.transportControls.playFromMediaId(item.mediaId, null)
//        }
//    }

    private val mConnectionCallback: ConnectionCallback = object : ConnectionCallback() {
        override fun onConnected() {
//            mMediaBrowser.subscribe(mMediaBrowser.root, mSubscriptionCallback)
            mMediaBrowser.sessionToken.also { token ->
                val mediaController = MediaController(this@MainActivity, token)
                mediaController.registerCallback(mMediaControllerCallback)
                setMediaController(mediaController)
            }
//            updatePlaybackState(mediaController.playbackState)
//            updateMetadata(mediaController.metadata)
        }
    }

    private val mMediaControllerCallback: MediaController.Callback = object : MediaController.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadata?) {
            metadata?.let {
                val track = MusicLibrary.tracks.find { it.metadata.description.mediaId == metadata.description.mediaId }!!
                viewModel.selectTrack(track)
            }
        }

        override fun onPlaybackStateChanged(state: PlaybackState?) {
            viewModel.updatePlaybackState(state)
        }

        override fun onSessionDestroyed() {
            viewModel.updatePlaybackState(null)
        }
    }

    override fun onStart() {
        super.onStart()
        mMediaBrowser.connect()
    }

    public override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    public override fun onStop() {
        super.onStop()
        // (see "stay in sync with the MediaSession")
        mediaController?.unregisterCallback(mMediaControllerCallback)
        mMediaBrowser.disconnect()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            9999 -> if (data != null) showSongs(data.data!!)
        }
    }

    private fun showSongs(treeUri: Uri) {
        val dir = DocumentFile.fromTreeUri(applicationContext, treeUri)
        if (dir == null || !dir.isDirectory) return
        with (getPreferences(Context.MODE_PRIVATE).edit()) {
            putString(SHARED_PREFS_URI_TAG, treeUri.toString())
            apply()
        }
        val tracksOrNone = dir.listFiles().map { f ->
            try {
                val r = MediaMetadataRetriever()
                r.setDataSource(applicationContext, f.uri)
                val builder = MediaMetadata.Builder().apply {
                    putString(MediaMetadata.METADATA_KEY_MEDIA_ID, f.uri.path.toString())
                    putString(MediaMetadata.METADATA_KEY_TITLE, f.name)
                    putLong(MediaMetadata.METADATA_KEY_DURATION, r.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong())
//                    putString(MediaMetadata.METADATA_KEY_DURATION, r.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))
                }
                val metadata = builder.build()
//                Log.e(f.name, r.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString())
//                Log.e(f.name, metadata.getString(MediaMetadata.METADATA_KEY_MEDIA_ID))
                Track(f.name ?: "No name", f.uri, metadata)
            } catch (e: Exception) {
//                Log.e("EXCEPT", e.toString())
                null
            }
        }

        viewModel.updateTracks(tracksOrNone.filterNotNull())
    }
}