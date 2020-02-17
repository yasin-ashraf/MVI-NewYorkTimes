package com.yasin.okcredit.ui.movies

import com.yasin.okcredit.data.entity.MovieNews

/**
 * Created by Yasin on 17/2/20.
 */
data class MoviesViewState(
    val isLoading : Boolean = false,
    val error : String = "",
    val isEmpty : Boolean = false,
    val adapterList : List<MovieNews> = emptyList()
)

sealed class MoviesViewEvent {
    object ScreenLoadEvent : MoviesViewEvent()
}

sealed class MoviesViewResult {
    data class ScreenLoadResult(val list: List<MovieNews>, val error: String = "") : MoviesViewResult()
}