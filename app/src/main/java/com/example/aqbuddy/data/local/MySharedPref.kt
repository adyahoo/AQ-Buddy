package com.example.aqbuddy.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.example.aqbuddy.utils.Constants

@Singleton
class MySharedPref @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPref = context.getSharedPreferences(Constants.MY_PREF_KEY, Context.MODE_PRIVATE)

    fun setPref(key: String, value: String) {
        sharedPref.edit {
            putString(key, value)
            apply()
        }
    }

    fun setPref(key: String, value: Boolean) {
        sharedPref.edit {
            putBoolean(key, value)
            apply()
        }
    }

    fun getStringPref(key: String): String = sharedPref.getString(key, null)!!
    fun getBoolPref(key: String): Boolean = sharedPref.getBoolean(key, false)
}