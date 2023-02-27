package com.thoughtworks.androidtrain.helloworld.utils

import android.app.Activity
import android.content.Context

class SharedPreferenceUtils {
    companion object {
        fun writeString(activity: Activity, key: String, value: String) {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }
        fun readString(activity: Activity, key: String, defaultValue: String): String? {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
            return sharedPref.getString(key, defaultValue)
        }

        fun writeBoolean(activity: Activity, key: String, value: Boolean) {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putBoolean(key, value)
                apply()
            }
        }
        fun readBoolean(activity: Activity, key: String, defaultValue: Boolean): Boolean? {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
            return sharedPref.getBoolean(key, defaultValue)
        }
    }
}