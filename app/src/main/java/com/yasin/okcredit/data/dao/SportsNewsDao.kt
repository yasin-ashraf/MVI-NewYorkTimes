package com.yasin.okcredit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yasin.okcredit.GET_ALL_SPORTS_NEWS_QUERY
import com.yasin.okcredit.GET_SPORTS_NEWS_DETAILS_QUERY
import com.yasin.okcredit.data.entity.SportsNews
import io.reactivex.Observable

/**
 * Created by Yasin on 17/2/20.
 */
@Dao
interface SportsNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSportsNews(news : Array<SportsNews>)

    @Query(GET_ALL_SPORTS_NEWS_QUERY)
    fun getAllSportsNews() : Observable<List<SportsNews>>

    @Query(GET_SPORTS_NEWS_DETAILS_QUERY)
    fun getNewsDetails(id : String) : Observable<SportsNews>
}