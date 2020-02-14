package com.yasin.okcredit.ui.home

import com.yasin.okcredit.COVER_PHOTO
import com.yasin.okcredit.FETCH_TIME_OUT
import com.yasin.okcredit.THUMBNAIL
import com.yasin.okcredit.data.SessionManager
import com.yasin.okcredit.data.dataModels.ResultsItem
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.data.repository.LocalRepository
import com.yasin.okcredit.data.repository.RemoteRepository
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.ui.home.HomeViewResult.ScreenLoadResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
class HomeRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val sessionManager: SessionManager
) {

    fun getHomeNews(): Observable<Lce<ScreenLoadResult>>? {
        if(!shouldFetch()){
            return localRepository.getHomeNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    if(it.isNullOrEmpty()){
                        Lce.Error(ScreenLoadResult(it, "empty list"))
                    }else {
                        Lce.Content(ScreenLoadResult(it))
                    }
                }.startWith(Lce.Loading())
        }else {
            return remoteRepository.fetchHomeNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    if(it.results?.isNullOrEmpty() == false){
                        val newsArray = mutableListOf<HomeNews>()
                        it.results.forEach { item ->
                            val homeNews = HomeNews(
                                id = item.createdDate,
                                title = item.title,
                                author = item.byline,
                                abstract = item.abstract,
                                coverImage = getCoverImage(item),
                                articleLink = item.url,
                                thumbnail = getThumbnail(item),
                                publishedDate = item.publishedDate)
                            newsArray.add(homeNews)
                        }
                        localRepository.insertHomeNewsItem(newsArray.toTypedArray())
                        sessionManager.lastFetchTime = Calendar.getInstance().timeInMillis
                    }
                }.flatMapObservable {
                    localRepository.getHomeNews()
                }.map {
                    if(it.isNullOrEmpty()){
                        Lce.Error(ScreenLoadResult(it, "empty list"))
                    }else {
                        Lce.Content(ScreenLoadResult(it))
                    }
                }.onErrorReturn {
                    Lce.Error(ScreenLoadResult(emptyList(),error = it.localizedMessage))
                }.startWith(Lce.Loading())
        }
    }


    private fun getThumbnail(it: ResultsItem): String {
        if (it.multimedia.isNullOrEmpty()) return ""
        return it.multimedia.find {
            it.format == THUMBNAIL
        }?.url ?: ""

    }

    private fun getCoverImage(it: ResultsItem): String {
        if (it.multimedia.isNullOrEmpty()) return ""
        return it.multimedia.find {
            it.format == COVER_PHOTO
        }?.url ?: ""
    }

    private fun shouldFetch() : Boolean{
        return (Calendar.getInstance().timeInMillis - ( sessionManager.lastFetchTime ?: 0L ) > FETCH_TIME_OUT)
    }
}