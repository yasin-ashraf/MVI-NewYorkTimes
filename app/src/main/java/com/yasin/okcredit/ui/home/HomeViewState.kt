package com.yasin.okcredit.ui.home

import com.yasin.okcredit.data.entity.HomeNews

/**
 * Created by Yasin on 10/2/20.
 */
data class HomeViewState(
    val isLoading : Boolean = false,
    val error : String = "",
    val isEmpty : Boolean = false,
    val adapterList : List<HomeNews> = emptyList()
)

sealed class HomeViewEvent {
    object ScreenLoadEvent : HomeViewEvent()
}

sealed class HomeViewResult {
    data class ScreenLoadResult(val list: List<HomeNews>, val error: String = "") : HomeViewResult()
}