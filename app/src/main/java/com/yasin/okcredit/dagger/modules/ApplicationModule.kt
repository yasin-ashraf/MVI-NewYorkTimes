package com.yasin.okcredit.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.yasin.okcredit.BASE_URL
import com.yasin.okcredit.network.NewYorkTimesApi
import com.yasin.okcredit.dagger.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Yasin on 4/2/20.
 */
@Module(includes = [ContextModule::class])
class ApplicationModule {

    @Provides
    @ApplicationScope
    fun provideApis(retrofit: Retrofit) : NewYorkTimesApi {
        return retrofit.create(NewYorkTimesApi::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideGson() : Gson {
        return GsonBuilder().create()
    }

    @Provides
    @ApplicationScope
    fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor) :  OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}