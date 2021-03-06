package com.yasin.okcredit.ui.movies

import androidx.lifecycle.ViewModel
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.viewState.NewsViewEvent
import com.yasin.okcredit.viewState.NewsViewEvent.*
import com.yasin.okcredit.viewState.NewsViewResult
import com.yasin.okcredit.viewState.NewsViewResult.*
import com.yasin.okcredit.viewState.NewsViewState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yasin on 17/2/20.
 */
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val eventEmitter: PublishSubject<NewsViewEvent> = PublishSubject.create()
    private lateinit var disposable: Disposable
    val viewState: Observable<NewsViewState>

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

    fun processInput(movieViewEvent: NewsViewEvent?) {
        eventEmitter.onNext(movieViewEvent ?: ScreenLoadEvent)
    }

    private fun Observable<NewsViewEvent>.eventToResult(): Observable<Lce<out NewsViewResult>> {
        return publish { o ->
            o.ofType(ScreenLoadEvent::class.java).onScreenLoad()
        }
    }

    private fun Observable<Lce<out NewsViewResult>>.resultToViewState(): Observable<NewsViewState> {
        return scan(NewsViewState()) { vs, result ->
            when (result) {
                is Lce.Content -> {
                    when (result.packet) {
                        is ScreenLoadResult -> {
                            vs.copy(isLoading = false, isEmpty = false,adapterList = result.packet.list, error = "")
                        }
                        else -> {
                            error("invalid event result!!")
                        }
                    }
                }

                is Lce.Loading -> {
                    vs.copy(isLoading = true, error = "")
                }

                is Lce.Error -> {
                    when (result.packet) {
                        is ScreenLoadResult -> {
                            if(result.packet.list.isEmpty()){
                                vs.copy(isLoading = false, isEmpty = true,error = result.packet.error)
                            }else{
                                vs.copy(isLoading = false,error = result.packet.error)
                            }
                        }
                        else -> {
                            error("invalid event result!!")
                        }
                    }
                }
            }
        }
    }


    private fun Observable<ScreenLoadEvent>.onScreenLoad(): Observable<Lce<out NewsViewResult>> {
        return switchMap {
            moviesRepository.getMovieNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}