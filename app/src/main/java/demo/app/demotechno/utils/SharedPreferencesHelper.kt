package demo.app.demotechno.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferencesHelper {
    private const val PREF_NAME = "my_pref"
    private const val KEY_USER_SIGNED_IN = "user_signed_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun setUserSignedIn(context: Context, signedIn: Boolean) {
        getSharedPreferences(context).edit {
            putBoolean(KEY_USER_SIGNED_IN, signedIn)
        }
    }

    fun isUserSignedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_USER_SIGNED_IN, false)
    }
}