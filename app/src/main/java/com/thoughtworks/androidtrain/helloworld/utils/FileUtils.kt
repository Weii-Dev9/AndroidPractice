package com.thoughtworks.androidtrain.helloworld.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class FileUtils {
    companion object {
        fun readFileFromRaw(context: Context, rawId: Int): String? {
            val str: InputStream = context.resources.openRawResource(rawId)
            val reader = BufferedReader(InputStreamReader(str))
            var jsonStr: String? = ""
            var line: String? = ""
            try {
                while (reader.readLine().also { line = it } != null) {
                    jsonStr += line
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return jsonStr
        }

    }
}