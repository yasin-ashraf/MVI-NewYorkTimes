package com.yasin.okcredit

import android.app.Application
import android.content.Context
import com.yasin.okcredit.dagger.DaggerMainComponent
import com.yasin.okcredit.dagger.MainComponent
import com.yasin.okcredit.dagger.modules.ContextModule

/**
 * Created by Yasin on 9/2/20.
 */
class OkCredit : Application() {

    lateinit var mainComponent: MainComponent
    private set

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        fun getApp(context: Context) : OkCredit {
            return context.applicationContext as OkCredit
        }
    }
}