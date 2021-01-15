package pl.krzysztofruczkowski.pwr4

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import pl.krzysztofruczkowski.pwr4.models.Track

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
        viewModel.updateTracks(dir.listFiles().map { f -> Track(f.name ?: "No name", f.uri) })
    }
}