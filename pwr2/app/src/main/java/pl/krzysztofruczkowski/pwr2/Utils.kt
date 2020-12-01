package pl.krzysztofruczkowski.pwr2

import android.content.Context
import android.content.res.Resources
import java.io.IOException
import java.util.*

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

fun getImageIdByName(fileName: String, resources: Resources, appPackageName: String): Int =
    resources.getIdentifier(fileName, "drawable", appPackageName)
