package com.yasin.okcredit.ui.home

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yasin on 4/2/20.
 */
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val eventEmitter: PublishSubject<HomeViewEvent> = PublishSubject.create()

    fun processInput(homeViewEvent: HomeViewEvent?) {

    }
}