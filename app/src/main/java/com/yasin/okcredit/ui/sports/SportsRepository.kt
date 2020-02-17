package com.yasin.okcredit.ui.sports

import com.yasin.okcredit.COVER_PHOTO
import com.yasin.okcredit.FETCH_TIME_OUT
import com.yasin.okcredit.SPORTS_NEWS
import com.yasin.okcredit.THUMBNAIL
import com.yasin.okcredit.data.SessionManager
import com.yasin.okcredit.data.dataModels.ResultsItem
import com.yasin.okcredit.data.entity.News
import com.yasin.okcredit.data.repository.LocalRepository
import com.yasin.okcredit.data.repository.RemoteRepository
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.viewState.NewsViewResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Yasin on 17/2/20.
 */
class SportsRepository @Inject constructor(private val localRepository: LocalRepository,
                                           private val remoteRepository: RemoteRepository,
                                           private val sessionManager: SessionManager) {

    fun getScienceNews(): Observable<Lce<NewsViewResult.ScreenLoadResult>>? {
        if(!shouldFetch()){
            return localRepository.getSportsNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    if(it.isNullOrEmpty()){
                        Lce.Error(NewsViewResult.ScreenLoadResult(it, "empty list"))
                    }else {
                        Lce.Content(NewsViewResult.ScreenLoadResult(it))
                    }
                }.startWith(Lce.Loading())
        }else {
            return remoteRepository.fetchSportsNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    if(it.results?.isNullOrEmpty() == false){
                        val newsArray = mutableListOf<News>()
                        it.results.forEach { item ->
                            val homeNews = News(
                                id = item.createdDate,
                                title = item.title,
                                author = item.byline,
                                abstractSt = item.abstract,
                                coverImage = getCoverImage(item),
                                articleLink = item.url,
                                thumbnail = getThumbnail(item),
                                publishedDate = item.publishedDate,
                                newsType = SPORTS_NEWS)
                            newsArray.add(homeNews)
                        }
                        localRepository.insertNewsItem(newsArray.toTypedArray())
                        sessionManager.lastFetchTimeSportsNews = Calendar.getInstance().timeInMillis
                    }
                }.flatMapObservable {
                    localRepository.getSportsNews()
                }.map {
                    if(it.isNullOrEmpty()){
                        Lce.Error(NewsViewResult.ScreenLoadResult(it, "empty list"))
                    }else {
                        Lce.Content(NewsViewResult.ScreenLoadResult(it))
                    }
                }.onErrorReturn {
                    Lce.Error(
                        NewsViewResult.ScreenLoadResult(
                            emptyList(),
                            error = it.localizedMessage
                        )
                    )
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
        return (Calendar.getInstance().timeInMillis - ( sessionManager.lastFetchTimeSportsNews ?: 0L ) > FETCH_TIME_OUT)
    }
}