package com.yasin.okcredit.dagger.modules

import android.content.Context
import androidx.room.Room
import com.yasin.okcredit.DATABASE_NAME
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.NewsDatabase
import com.yasin.okcredit.data.dao.HomeNewsDao
import com.yasin.okcredit.data.dao.MovieNewsDao
import com.yasin.okcredit.data.dao.ScienceNewsDao
import com.yasin.okcredit.data.dao.SportsNewsDao
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
    fun provideNewsDao(handzapDatabase: NewsDatabase) : HomeNewsDao {
        return handzapDatabase.homeNewsDao()
    }

    @Provides
    @ApplicationScope
    fun provideMovieNewsDao(handzapDatabase: NewsDatabase) : MovieNewsDao {
        return handzapDatabase.moviesNewsDao()
    }

    @Provides
    @ApplicationScope
    fun provideScienceNewsDao(handzapDatabase: NewsDatabase) : ScienceNewsDao {
        return handzapDatabase.scienceNewsDao()
    }

    @Provides
    @ApplicationScope
    fun provideSportsNewsDao(handzapDatabase: NewsDatabase) : SportsNewsDao {
        return handzapDatabase.sportsNewsDao()
    }
}