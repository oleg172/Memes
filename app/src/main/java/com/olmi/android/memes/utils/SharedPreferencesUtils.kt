package com.olmi.android.memes.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/*
* Utility class for work with sharedPreferences
* */
object SharedPreferencesUtils {

    private const val PREF_NAME = "MEMES_PREFS"
    private const val DEFAULT_STRING_VALUE = "no_data_found"

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun insertData(sharedPreferences: SharedPreferences, key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun insertData(sharedPreferences: SharedPreferences, key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun retriveData(sharedPreferences: SharedPreferences, key: String): String {
        return try {
            sharedPreferences.getString(key, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE
        } catch (e: ClassCastException) {
            Log.e(
                SharedPreferencesUtils.javaClass.name,
                "Can't get data from  SharedPreferences by key: $key", e
            )
            DEFAULT_STRING_VALUE
        }
    }

    fun deleteData(sharedPreferences: SharedPreferences, key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}
