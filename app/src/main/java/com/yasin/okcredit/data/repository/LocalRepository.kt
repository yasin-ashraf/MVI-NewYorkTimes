package com.yasin.okcredit.data.repository

import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.dao.HomeNewsDao
import com.yasin.okcredit.data.entity.HomeNews
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
@ApplicationScope
class LocalRepository @Inject constructor(private val homeNewsDao: HomeNewsDao) {

    fun getHomeNews(): Observable<List<HomeNews>> {
        return homeNewsDao.getAllHomeNews()
    }

    fun insertHomeNewsItem(news : Array<HomeNews>) {
        homeNewsDao.saveHomeNews(news)
    }
}