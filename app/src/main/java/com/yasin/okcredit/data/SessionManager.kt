package com.yasin.okcredit.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
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

    var lastFetchTimeHomeNews: Long?
        get() = pref.getLong(LAST_FETCH_TIME_HOME_NEWS, 0L)
        set(time) {
            editor.putLong(LAST_FETCH_TIME_HOME_NEWS, time ?: 0L)
            editor.apply()
        }

    var lastFetchTimeMovieNews: Long?
        get() = pref.getLong(LAST_FETCH_TIME_MOVIE_NEWS, 0L)
        set(time) {
            editor.putLong(LAST_FETCH_TIME_MOVIE_NEWS, time ?: 0L)
            editor.apply()
        }

    var lastFetchTimeScienceNews: Long?
        get() = pref.getLong(LAST_FETCH_TIME_SCIENCE_NEWS, 0L)
        set(time) {
            editor.putLong(LAST_FETCH_TIME_SCIENCE_NEWS, time ?: 0L)
            editor.apply()
        }

    var lastFetchTimeSportsNews: Long?
        get() = pref.getLong(LAST_FETCH_TIME_SPORTS_NEWS, 0L)
        set(time) {
            editor.putLong(LAST_FETCH_TIME_SPORTS_NEWS, time ?: 0L)
            editor.apply()
        }


    companion object {
        private const val PREF_NAME = "OkCreditYasinSession"
        private const val LAST_FETCH_TIME_HOME_NEWS = "lastFetchTimeHome"
        private const val LAST_FETCH_TIME_MOVIE_NEWS = "lastFetchTimeMovie"
        private const val LAST_FETCH_TIME_SCIENCE_NEWS = "lastFetchTimeScience"
        private const val LAST_FETCH_TIME_SPORTS_NEWS = "lastFetchTimeSports"
    }
}
