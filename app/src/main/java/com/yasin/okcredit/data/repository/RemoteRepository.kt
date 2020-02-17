package com.yasin.okcredit.data.repository

import com.yasin.okcredit.API_KEY
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.dataModels.NewsResponse
import com.yasin.okcredit.network.NewYorkTimesApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
@ApplicationScope
class RemoteRepository @Inject constructor(private val newYorkTimesApi: NewYorkTimesApi) {

    fun fetchHomeNews() : Single<NewsResponse>{
        return newYorkTimesApi.fetchHomeNews(API_KEY)
    }

    fun fetchMovieNews() : Single<NewsResponse>{
        return newYorkTimesApi.fetchMovieNews(API_KEY)
    }

    fun fetchScienceNews() : Single<NewsResponse>{
        return newYorkTimesApi.fetchScienceNews(API_KEY)
    }

    fun fetchSportsNews() : Single<NewsResponse>{
        return newYorkTimesApi.fetchSportsNews(API_KEY)
    }
}