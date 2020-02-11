package com.yasin.okcredit.ui.home

import com.yasin.okcredit.data.entity.HomeNews

/**
 * Created by Yasin on 10/2/20.
 */
data class HomeViewState(
    val adapterList : List<HomeNews>
)

sealed class HomeViewEvent {
    object ScreenLoadEvent : HomeViewEvent()
    object ScreenReloadEvent : HomeViewEvent()
}