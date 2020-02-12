package com.yasin.okcredit.ui.home

import androidx.lifecycle.ViewModel
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.ui.home.HomeViewEvent.*
import com.yasin.okcredit.ui.home.HomeViewResult.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
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
            .doOnNext { Timber.d("----- event $it") }
            .eventToResult()
            .doOnNext { Timber.d("----- result $it") }
            .share()
            .also { result ->
                viewState = result
                    .resultToViewState()
                    .doOnNext { Timber.d("----- vs $it") }
                    .replay(1)
                    .autoConnect(1) { disposable = it }
            }

    }

    fun processInput(homeViewEvent: HomeViewEvent?) {
        eventEmitter.onNext(homeViewEvent ?: ScreenLoadEvent)
    }

    private fun Observable<HomeViewEvent>.eventToResult(): Observable<Lce<out HomeViewResult>> {
        return publish { o ->
            Observable.merge(
                o.ofType(LoadNewsEvent::class.java).onLoadNews(),
                o.ofType(ScreenLoadEvent::class.java).onScreenLoad()
            )
        }
    }

    private fun Observable<Lce<out HomeViewResult>>.resultToViewState(): Observable<HomeViewState> {
        return scan(HomeViewState()) { vs, result ->
            when (result) {
                is Lce.Content -> {
                    when (result.packet) {
                        is ScreenLoadResult -> {
                            vs.copy(isLoading = true)
                        }

                        is LoadNewsResult -> {
                            vs.copy(isLoading = false, adapterList = result.packet.list)
                        }
                    }
                }

                is Lce.Loading -> {
                    vs.copy(isLoading = true)
                }

                is Lce.Error -> {
                    when (result.packet) {
                        is LoadNewsResult -> {
                            vs.copy(isLoading = false)
                        }
                        else -> throw RuntimeException("Unexpected result LCE state")
                    }
                }
            }
        }
            .distinctUntilChanged()
    }


    private fun Observable<ScreenLoadEvent>.onScreenLoad(): Observable<Lce<ScreenLoadResult>> {
        return map { Lce.Content(ScreenLoadResult) }
    }

    private fun Observable<LoadNewsEvent>.onLoadNews(): Observable<Lce<LoadNewsResult>> {
        return switchMap {
            homeRepository.getHomeNews()
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.isNullOrEmpty()) {
                        Lce.Error(LoadNewsResult(it))
                    } else {
                        Lce.Content(LoadNewsResult(it))
                    }
                }
                .startWith(Lce.Loading())
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeRepository.onCleared()
        disposable.dispose()
    }
}