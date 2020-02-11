package com.yasin.okcredit.data.repository

import com.yasin.okcredit.API_KEY
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.data.dataModels.HomeNewsResponse
import com.yasin.okcredit.network.NewYorkTimesApi
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by Yasin on 11/2/20.
 */
@ApplicationScope
class RemoteRepository @Inject constructor(private val newYorkTimesApi: NewYorkTimesApi) {

    fun fetchHomeNews() : Single<HomeNewsResponse>{
        return newYorkTimesApi.fetchHomeNews(API_KEY)
    }
}