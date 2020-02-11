package com.yasin.okcredit.ui.home

import com.yasin.okcredit.data.entity.HomeNews

/**
 * Created by Yasin on 10/2/20.
 */
data class HomeViewState(
    val isLoading : Boolean = false,
    val adapterList : List<HomeNews> = emptyList()
)

sealed class HomeViewEvent {
    object ScreenLoadEvent : HomeViewEvent()
    object LoadNewsEvent : HomeViewEvent()
}

sealed class HomeViewResult {
    object ScreenLoadResult : HomeViewResult()
    data class LoadNewsResult(val list: List<HomeNews>) : HomeViewResult()
}