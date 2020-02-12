package com.yasin.okcredit.ui.home

import androidx.lifecycle.ViewModel
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.ui.home.HomeViewEvent.ScreenLoadEvent
import com.yasin.okcredit.ui.home.HomeViewResult.ScreenLoadResult
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yasin on 4/2/20.
 */
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val eventEmitter: PublishSubject<HomeViewEvent> = PublishSubject.create()
    private lateinit var disposable: Disposable
    val viewState: Observable<HomeViewState>

    init {
        eventEmitter
            .eventToResult()
            .share()
            .also { result ->
                viewState = result
                    .resultToViewState()
                    .replay(1)
                    .autoConnect(1) { disposable = it }
            }

    }

    fun processInput(homeViewEvent: HomeViewEvent?) {
        eventEmitter.onNext(homeViewEvent ?: ScreenLoadEvent)
    }

    private fun Observable<HomeViewEvent>.eventToResult(): Observable<Lce<out HomeViewResult>> {
        return publish { o ->
            o.ofType(ScreenLoadEvent::class.java).onScreenReLoad()
        }
    }

    private fun Observable<Lce<out HomeViewResult>>.resultToViewState(): Observable<HomeViewState> {
        return scan(HomeViewState()) { vs, result ->
            when (result) {
                is Lce.Content -> {
                    when (result.packet) {
                        is ScreenLoadResult -> {
                            vs.copy(isLoading = false, adapterList = result.packet.list)
                        }
                        else -> {
                            error("invalid event result!!")
                        }
                    }
                }

                is Lce.Loading -> {
                    vs.copy(isLoading = true)
                }

                is Lce.Error -> {
                    when (result.packet) {
                        is ScreenLoadResult -> {
                            vs.copy(isLoading = false,error = result.packet.error)
                        }
                        else -> {
                            error("invalid event result!!")
                        }
                    }
                }
            }
        }
    }


    private fun Observable<ScreenLoadEvent>.onScreenReLoad(): Observable<Lce<out HomeViewResult>> {
        return switchMap {
            homeRepository.getHomeNews()
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.isNullOrEmpty()) {
                        Lce.Error(ScreenLoadResult(it,"error fetching news.."))
                    } else {
                        Lce.Content(ScreenLoadResult(it))
                    }
                }.startWith(Lce.Loading())
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeRepository.onCleared()
        disposable.dispose()
    }
}