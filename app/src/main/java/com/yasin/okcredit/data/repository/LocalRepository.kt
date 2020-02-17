package com.yasin.okcredit.data.repository

import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.dao.HomeNewsDao
import com.yasin.okcredit.data.dao.MovieNewsDao
import com.yasin.okcredit.data.dao.ScienceNewsDao
import com.yasin.okcredit.data.dao.SportsNewsDao
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.data.entity.MovieNews
import com.yasin.okcredit.data.entity.ScienceNews
import com.yasin.okcredit.data.entity.SportsNews
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
@ApplicationScope
class LocalRepository @Inject constructor(private val homeNewsDao: HomeNewsDao,
                                          private val movieNewsDao: MovieNewsDao,
                                          private val scienceNewsDao: ScienceNewsDao,
                                          private val sportsNewsDao: SportsNewsDao) {

    fun getHomeNews(): Observable<List<HomeNews>> {
        return homeNewsDao.getAllHomeNews()
    }

    fun insertHomeNewsItem(news : Array<HomeNews>) {
        homeNewsDao.saveHomeNews(news)
    }

    fun getHomeNewsDetails(id : String) : Observable<HomeNews> {
       return homeNewsDao.getNewsDetails(id)
    }

    fun getMovieNews(): Observable<List<MovieNews>> {
        return movieNewsDao.getAllMovieNews()
    }

    fun insertMovieNewsItem(news : Array<MovieNews>) {
        movieNewsDao.saveMovieNews(news)
    }

    fun getMovieNewsDetails(id : String) : Observable<MovieNews> {
        return movieNewsDao.getNewsDetails(id)
    }

    fun getScienceNews(): Observable<List<ScienceNews>> {
        return scienceNewsDao.getAllScienceNews()
    }

    fun insertScienceNewsItem(news : Array<ScienceNews>) {
        scienceNewsDao.saveScienceNews(news)
    }

    fun getScienceNewsDetails(id : String) : Observable<ScienceNews> {
        return scienceNewsDao.getNewsDetails(id)
    }

    fun getSportsNews(): Observable<List<SportsNews>> {
        return sportsNewsDao.getAllSportsNews()
    }

    fun insertSportsNewsItem(news : Array<SportsNews>) {
        sportsNewsDao.saveSportsNews(news)
    }

    fun getSportsNewsDetails(id : String) : Observable<SportsNews> {
        return sportsNewsDao.getNewsDetails(id)
    }
}