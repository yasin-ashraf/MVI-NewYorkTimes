package com.yasin.okcredit.ui.home

import com.yasin.okcredit.COVER_PHOTO
import com.yasin.okcredit.THUMBNAIL
import com.yasin.okcredit.data.dataModels.ResultsItem
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.data.repository.LocalRepository
import com.yasin.okcredit.data.repository.RemoteRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
class HomeRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private lateinit var disposable: Disposable

    fun getHomeNews(): Observable<List<HomeNews>> {
        refreshProducts() //todo : return loading state from here
        return localRepository.getHomeNews()
    }

    private fun refreshProducts() {
        disposable = remoteRepository.fetchHomeNews()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ response ->
                if(response?.results?.isNullOrEmpty() == false){
                    val newsArray = mutableListOf<HomeNews>()
                    response.results.forEach {
                        val homeNews = HomeNews(
                            id = it.createdDate,
                            title = it.title,
                            author = it.byline,
                            abstract = it.abstract,
                            coverImage = getCoverImage(it),
                            articleLink = it.url,
                            thumbnail = getThumbnail(it),
                            publishedDate = it.publishedDate)
                        newsArray.add(homeNews)
                    }
                    localRepository.insertHomeNewsItem(newsArray.toTypedArray())
                }
            },
                {
                    Timber.e(it, "Error refresh Home News")
                })
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

    fun onCleared() {
        if(::disposable.isInitialized)
            disposable.dispose()
    }
}