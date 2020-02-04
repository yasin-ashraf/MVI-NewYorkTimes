package com.yasin.okcredit.dagger.modules

import android.content.Context
import androidx.room.Room
import com.yasin.okcredit.DATABASE_NAME
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.NewsDatabase
import com.yasin.okcredit.data.dao.NewsDao
import dagger.Module
import dagger.Provides

/**
 * Created by Yasin on 4/2/20.
 */
@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context) : NewsDatabase {
        return Room.databaseBuilder(context,NewsDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideNewsDao(handzapDatabase: NewsDatabase) : NewsDao {
        return handzapDatabase.newsDao()
    }

}