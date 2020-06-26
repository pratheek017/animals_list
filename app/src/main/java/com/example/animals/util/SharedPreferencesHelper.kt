package com.example.animals.util

import android.content.Context

import androidx.preference.PreferenceManager

/**
 * A helper class to store and retrieve data from shared preferences
 */
class SharedPreferencesHelper(context: Context) {

    /**
     * The shared preferences key to store the api key
     */
    private val PREF_API_KEY = "Api key"

    /**
     * The Android shared preferences
     */
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    /**
     * Saves the api key in the shared preferences
     * @param key the api key
     */
    fun saveApiKey(key: String?){
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    /**
     * This is a one line function in Kotlin that returns the value calculated.
     * The above function also can be converted to one line function
     * Returns the api key stored in the shared preferences.
     * @return the stored api key in shared preferences,
     *         null if nothing is stored in the shared preferences
     */
    fun getApiKey() = prefs.getString(PREF_API_KEY, null)
}