package com.yasin.okcredit.data.repository

import com.yasin.okcredit.HOME_NEWS
import com.yasin.okcredit.MOVIES_NEWS
import com.yasin.okcredit.SCIENCE_NEWS
import com.yasin.okcredit.SPORTS_NEWS
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.dao.NewsDao
import com.yasin.okcredit.data.entity.News
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
@ApplicationScope
class LocalRepository @Inject constructor(private val newsDao: NewsDao) {

    fun getHomeNews(): Observable<List<News>> {
        return newsDao.getAllHomeNews(HOME_NEWS)
    }

    fun insertNewsItem(news : Array<News>) {
        newsDao.saveNews(news)
    }

    fun getNewsDetails(id : String) : Observable<News> {
       return newsDao.getNewsDetails(id)
    }

    fun getMovieNews(): Observable<List<News>> {
        return newsDao.getAllMovieNews(MOVIES_NEWS)
    }

    fun getScienceNews(): Observable<List<News>> {
        return newsDao.getAllScienceNews(SCIENCE_NEWS)
    }

    fun getSportsNews(): Observable<List<News>> {
        return newsDao.getAllSportsNews(SPORTS_NEWS)
    }

}