package com.olmi.android.memes.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPreferencesUtils {

    private const val PREF_NAME = "MEMES_PREFS"
    private const val DEFAULT_STRING_VALUE = "no_data_found"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun insertData(context: Context, key: String, value: String) {
        val editor = getPrefs(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun insertData(context: Context, key: String, value: Int) {
        val editor = getPrefs(context).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun retriveData(context: Context, key: String): String {
        return try {
            getPrefs(context).getString(key, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE
        } catch (e: ClassCastException) {
            Log.e(
                SharedPreferencesUtils.javaClass.name,
                "Can't get data from  SharedPreferences by key: $key", e
            )
            DEFAULT_STRING_VALUE
        }
    }

    fun deleteData(context: Context, key: String) {
        val editor = getPrefs(context).edit()
        editor.remove(key)
        editor.apply()
    }
}
