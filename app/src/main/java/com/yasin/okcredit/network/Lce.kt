package com.yasin.okcredit.network

/**
 * Created by Yasin on 11/2/20.
 */

sealed class Lce<T> {
    class Loading<T> : Lce<T>()
    data class Content<T>(val packet: T) : Lce<T>()
    data class Error<T>(val packet: T) : Lce<T>()
}