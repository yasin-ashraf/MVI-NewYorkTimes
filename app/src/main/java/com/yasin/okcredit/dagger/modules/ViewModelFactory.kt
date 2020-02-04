package com.yasin.okcredit.dagger.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.yasin.okcredit.dagger.scope.ApplicationScope

import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Yasin on 4/2/20.
 */

@Suppress("UNCHECKED_CAST")
@ApplicationScope
class ViewModelFactory @Inject
constructor(private val viewModelProviderMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (viewModelProviderMap[modelClass] ?: error("")).get() as T
    }
}
