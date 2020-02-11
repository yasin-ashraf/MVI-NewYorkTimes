package com.yasin.okcredit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yasin.okcredit.data.dao.NewsDao
import com.yasin.okcredit.data.entity.HomeNews

/**
 * Created by Yasin on 4/2/20.
 */
@Database(entities = [HomeNews::class],version = 1,exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun newsDao() : NewsDao
}