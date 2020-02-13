package com.yasin.okcredit.ui.details

import java.text.SimpleDateFormat
import java.util.*

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
) {
    fun dateToFormat(dateToFormat: String): String {
        var formattedDate = ""
        if (dateToFormat.isNotEmpty() && dateToFormat != "null") {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateToFormat)
            formattedDate = outputFormat.format(date)
        }
        return formattedDate
    }
}

sealed class DetailViewEvent {
    data class OpenChromeEvent(val uri : String) : DetailViewEvent()
    data class LoadDetailEvent(val newsId : String, val newsType : String) : DetailViewEvent()
}

sealed class DetailViewEffect {
    data class OpenChromeEffect(var uri: String) : DetailViewEffect()
}

sealed class DetailViewResult {
    data class LoadDetailResult(val generalNews: GeneralNews, val error : String = "") : DetailViewResult()
    data class OpenChromeResult(var uri: String) : DetailViewResult()
}