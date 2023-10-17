package com.rob_product_common.utils // ktlint-disable package-name

import android.content.Context
import java.io.IOException

fun getJsonFromAssets(context: Context, fileName: String): String {
    val jsonString: String
    try {
        // Open the JSON file from the "assets" folder using an InputStream
        val inputStream = context.assets.open(fileName)

        // Read the contents of the file into a StringBuilder
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonString = String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
        return ""
    }
    return jsonString
}
