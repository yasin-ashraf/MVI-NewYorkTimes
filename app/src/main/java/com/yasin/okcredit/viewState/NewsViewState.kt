package com.yasin.okcredit.viewState

import com.yasin.okcredit.data.entity.News

/**
 * Created by Yasin on 17/2/20.
 */
data class NewsViewState(
    val isLoading : Boolean = false,
    val error : String = "",
    val isEmpty : Boolean = false,
    val adapterList : List<News> = emptyList()
)

sealed class NewsViewEvent {
    object ScreenLoadEvent : NewsViewEvent()
}

sealed class NewsViewResult {
    data class ScreenLoadResult(val list: List<News>, val error: String = "") : NewsViewResult()
}