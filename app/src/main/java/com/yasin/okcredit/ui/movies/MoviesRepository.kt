package com.yasin.okcredit.ui.movies

import com.yasin.okcredit.COVER_PHOTO
import com.yasin.okcredit.FETCH_TIME_OUT
import com.yasin.okcredit.MOVIES_NEWS
import com.yasin.okcredit.THUMBNAIL
import com.yasin.okcredit.data.SessionManager
import com.yasin.okcredit.data.dataModels.ResultsItem
import com.yasin.okcredit.data.entity.News
import com.yasin.okcredit.data.repository.LocalRepository
import com.yasin.okcredit.data.repository.RemoteRepository
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.viewState.NewsViewResult.ScreenLoadResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Yasin on 17/2/20.
 */
class MoviesRepository @Inject constructor(private val localRepository: LocalRepository,
                                           private val remoteRepository: RemoteRepository,
                                           private val sessionManager: SessionManager){

    fun getMovieNews(): Observable<Lce<ScreenLoadResult>>? {
        if(!shouldFetch()){
            return localRepository.getMovieNews()
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
            return remoteRepository.fetchMovieNews()
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
                                newsType = MOVIES_NEWS)
                            newsArray.add(homeNews)
                        }
                        localRepository.insertNewsItem(newsArray.toTypedArray())
                        sessionManager.lastFetchTimeMovieNews = Calendar.getInstance().timeInMillis
                    }
                }.flatMapObservable {
                    localRepository.getMovieNews()
                }.map {
                    if(it.isNullOrEmpty()){
                        Lce.Error(ScreenLoadResult(it, "empty list"))
                    }else {
                        Lce.Content(ScreenLoadResult(it))
                    }
                }.onErrorReturn {
                    Lce.Error(
                        ScreenLoadResult(
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
        return (Calendar.getInstance().timeInMillis - ( sessionManager.lastFetchTimeMovieNews ?: 0L ) > FETCH_TIME_OUT)
    }

}