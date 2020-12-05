package pl.krzysztofruczkowski.pwr2

import android.app.Activity
import android.content.Context
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun getImageIdByName(fileName: String, activity: Activity): Int =
    activity.resources.getIdentifier(fileName, "drawable", activity.packageName)
