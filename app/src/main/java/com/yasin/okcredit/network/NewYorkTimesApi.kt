package com.yasin.okcredit.network

import com.yasin.okcredit.data.dataModels.HomeNewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yasin on 4/2/20.
 */
interface NewYorkTimesApi {

    @GET("home.json")
    fun fetchHomeNews(@Query("api-key") apiKey : String) : Single<HomeNewsResponse>
}