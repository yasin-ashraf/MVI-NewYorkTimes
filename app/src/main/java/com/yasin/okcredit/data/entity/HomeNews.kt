package com.yasin.okcredit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yasin on 4/2/20.
 */
@Entity
data class HomeNews(
    @PrimaryKey
    val id : String,
    val title : String,
    val author : String,
    val thumbnail : String,
    val abstract : String,
    val coverImage : String,
    val articleLink : String
)