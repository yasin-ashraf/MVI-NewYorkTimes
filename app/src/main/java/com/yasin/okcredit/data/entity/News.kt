package com.yasin.okcredit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasin.okcredit.HOME_NEWS

/**
 * Created by Yasin on 4/2/20.
 */
@Entity
data class News(
    @PrimaryKey
    var id : String = "",
    var title : String? = "",
    var author : String? = "",
    var thumbnail : String? = "",
    var abstractSt : String? = "",
    var coverImage : String? = "",
    var articleLink : String? = "",
    var publishedDate : String? = "",
    var newsType : String? = HOME_NEWS
)