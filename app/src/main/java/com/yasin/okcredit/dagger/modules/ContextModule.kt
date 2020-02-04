package com.yasin.okcredit.dagger.modules

import android.content.Context
import com.yasin.okcredit.dagger.scope.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Yasin on 4/2/20.
 */
@Module
class ContextModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext() : Context {
        return context
    }
}