package com.yasin.okcredit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yasin.okcredit.GET_ALL_SCIENCE_NEWS_QUERY
import com.yasin.okcredit.GET_SCIENCE_NEWS_DETAILS_QUERY
import com.yasin.okcredit.data.entity.ScienceNews
import io.reactivex.Observable

/**
 * Created by Yasin on 17/2/20.
 */
@Dao
interface ScienceNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveScienceNews(news : Array<ScienceNews>)

    @Query(GET_ALL_SCIENCE_NEWS_QUERY)
    fun getAllScienceNews() : Observable<List<ScienceNews>>

    @Query(GET_SCIENCE_NEWS_DETAILS_QUERY)
    fun getNewsDetails(id : String) : Observable<ScienceNews>
}