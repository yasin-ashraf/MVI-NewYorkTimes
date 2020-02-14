package com.yasin.okcredit.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.yasin.okcredit.dagger.qualifiers.ApplicationContext
import com.yasin.okcredit.dagger.scope.ApplicationScope
import javax.inject.Inject

/**
 * Created by Yasin on 13/2/20.
 */
@ApplicationScope
class SessionManager @SuppressLint("CommitPrefEdits")
@Inject constructor(context: Context) {
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        val PRIVATE_MODE = 0
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var lastFetchTime: Long?
        get() = pref.getLong(LAST_FETCH_TIME, 0L)
        set(time) {
            editor.putLong(LAST_FETCH_TIME, time ?: 0L)
            editor.apply()
        }


    companion object {
        private const val PREF_NAME = "OkCreditYasinSession"
        private const val LAST_FETCH_TIME = "lastFetchTime"
    }
}
