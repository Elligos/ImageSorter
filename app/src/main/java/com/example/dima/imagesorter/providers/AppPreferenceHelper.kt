package com.example.dima.imagesorter.providers

import android.content.Context
import android.content.SharedPreferences
import com.example.dima.imagesorter.di.PreferenceInfo
import javax.inject.Inject


class AppPreferenceHelper @Inject constructor(context: Context,
                                              @PreferenceInfo private val prefFileName: String)
                                    : PreferenceHelper {

    companion object {
        private val PREF_KEY_ITEMS_POOLING = "PREF_KEY_USER_LOGGED_IN_MODE"
    }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getItemsPooling(): Int? =  mPrefs.getInt(PREF_KEY_ITEMS_POOLING, 0)

    override fun setItemsPooling(poolingType: Int?) {
        with (mPrefs.edit()) {
            putInt(PREF_KEY_ITEMS_POOLING, poolingType?:0)
            apply()
            //P.S.
            //apply() changes the in-memory SharedPreferences object immediately but writes the
            // updates to disk asynchronously. Alternatively, you can use commit() to write the data
            // to disk synchronously. But because commit() is synchronous, you should avoid calling
            // it from your main thread because it could pause your UI rendering.
        }

    }
}