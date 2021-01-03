package pl.krzysztofruczkowski.pwr4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.documentfile.provider.DocumentFile


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)
//        val i = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//        i.addCategory(Intent.CATEGORY_DEFAULT)
//        startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            9999 -> if (data != null) showSongs(data.data!!)
        }
    }

    private fun showSongs(treeUri: Uri) {
//        val uri = DocumentsContract.buildDocumentUriUsingTree(treeUri, DocumentsContract.getDocumentId(treeUri))
        val dir = DocumentFile.fromTreeUri(applicationContext, treeUri)
        if (dir == null || !dir.isDirectory) return
        val names = dir.listFiles().map { f -> f.name }
        Log.i("X", names.toString())


//        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
//
//        val projection = arrayOf(
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Audio.Media.DURATION
//        )
//
//        val cursor = managedQuery(
//                uri,
//                projection,
//                selection,
//                null,
//                null)
//
//        val songs: MutableList<String> = ArrayList()
//        while (cursor.moveToNext()) {
//            songs.add(cursor.getString(0).toString() + "||" + cursor.getString(1) + "||" + cursor.getString(2) + "||" + cursor.getString(3) + "||" + cursor.getString(4) + "||" + cursor.getString(5))
//        }
//        Log.i("Test", songs.toString())
    }
}