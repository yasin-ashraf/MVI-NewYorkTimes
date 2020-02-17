package com.yasin.okcredit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yasin.okcredit.data.dao.HomeNewsDao
import com.yasin.okcredit.data.dao.MovieNewsDao
import com.yasin.okcredit.data.dao.ScienceNewsDao
import com.yasin.okcredit.data.dao.SportsNewsDao
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.data.entity.MovieNews
import com.yasin.okcredit.data.entity.ScienceNews
import com.yasin.okcredit.data.entity.SportsNews

/**
 * Created by Yasin on 4/2/20.
 */
@Database(entities = [HomeNews::class,MovieNews::class,ScienceNews::class,SportsNews::class],version = 1,exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun homeNewsDao() : HomeNewsDao
    abstract fun moviesNewsDao(): MovieNewsDao
    abstract fun scienceNewsDao(): ScienceNewsDao
    abstract fun sportsNewsDao(): SportsNewsDao
}