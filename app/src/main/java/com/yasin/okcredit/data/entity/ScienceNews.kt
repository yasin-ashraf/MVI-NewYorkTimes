package com.yasin.okcredit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yasin on 10/2/20.
 */
@Entity
data class ScienceNews(
    @PrimaryKey
    val id : String,
    var title : String? = "",
    var author : String? = "",
    var thumbnail : String? = "",
    var abstractSt : String? = "",
    var coverImage : String? = "",
    var articleLink : String? = "",
    var publishedDate : String? = ""
)