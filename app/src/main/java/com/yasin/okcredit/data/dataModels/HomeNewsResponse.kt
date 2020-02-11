package com.yasin.okcredit.data.dataModels

import com.google.gson.annotations.SerializedName

data class HomeNewsResponse(@SerializedName("copyright")
                            val copyright: String = "",
                            @SerializedName("last_updated")
                            val lastUpdated: String = "",
                            @SerializedName("section")
                            val section: String = "",
                            @SerializedName("results")
                            val results: List<ResultsItem>?,
                            @SerializedName("num_results")
                            val numResults: Int = 0,
                            @SerializedName("status")
                            val status: String = "")