package com.yasin.okcredit.ui.details

/**
 * Created by Yasin on 12/2/20.
 */

data class DetailViewState(
    val isLoading : Boolean = false,
    val error: String? = "",
    val title : String? = "",
    val coverPhoto : String? = "",
    val author : String? = "",
    val published : String? = "",
    val link : String? = "",
    val abstract : String? = ""
)

sealed class DetailViewEvent {
    data class LoadDetailEvent(val newsId : String, val newsType : String) : DetailViewEvent()
}

sealed class DetailViewResult {
    data class LoadDetailResult(val generalNews: GeneralNews, val error : String = "") : DetailViewResult()
}