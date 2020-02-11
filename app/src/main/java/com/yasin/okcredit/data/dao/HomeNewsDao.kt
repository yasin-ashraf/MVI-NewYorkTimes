package com.yasin.okcredit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yasin.okcredit.GET_ALL_HOME_NEWS_QUERY
import com.yasin.okcredit.data.entity.HomeNews
import io.reactivex.Observable

/**
 * Created by Yasin on 4/2/20.
 */
@Dao
interface HomeNewsDao {

    @Insert(onConflict = REPLACE)
    fun saveHomeNews(news : Array<HomeNews>)

    @Query(GET_ALL_HOME_NEWS_QUERY)
    fun getAllHomeNews() : Observable<List<HomeNews>>
}