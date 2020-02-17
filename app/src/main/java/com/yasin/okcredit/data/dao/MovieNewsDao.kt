package com.yasin.okcredit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yasin.okcredit.GET_ALL_MOVIE_NEWS_QUERY
import com.yasin.okcredit.GET_MOVIE_NEWS_DETAILS_QUERY
import com.yasin.okcredit.data.entity.MovieNews
import io.reactivex.Observable

/**
 * Created by Yasin on 17/2/20.
 */
@Dao
interface MovieNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieNews(news : Array<MovieNews>)

    @Query(GET_ALL_MOVIE_NEWS_QUERY)
    fun getAllMovieNews() : Observable<List<MovieNews>>

    @Query(GET_MOVIE_NEWS_DETAILS_QUERY)
    fun getNewsDetails(id : String) : Observable<MovieNews>
}