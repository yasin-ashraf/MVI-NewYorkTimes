package com.yasin.okcredit.ui.details

import androidx.lifecycle.ViewModel
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.ui.details.DetailViewEvent.*
import com.yasin.okcredit.ui.details.DetailViewResult.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yasin on 12/2/20.
 */
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository): ViewModel() {

    private val eventEmitter : PublishSubject<DetailViewEvent> = PublishSubject.create()
    val viewState :Observable<DetailViewState>
    private lateinit var disposable : Disposable

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

    fun processInput(it: DetailViewEvent) {
        eventEmitter.onNext(it)
    }

    private fun Observable<Lce<out DetailViewResult>>.resultToViewState() : Observable<DetailViewState> {
        return scan(DetailViewState()) { vs, result ->
            when (result){
                is Lce.Content -> {
                    when(result.packet) {
                        is LoadDetailResult -> {
                            val details = result.packet.generalNews
                            vs.copy(
                                isLoading = false,
                                title = details?.title,
                                abstract = details.abstract,
                                coverPhoto = details.coverImage,
                                author = details.author,
                                link = details.articleLink,
                                published = details.articleLink
                            )
                        }
                    }
                }
                is Lce.Error -> {
                    when(result.packet) {
                        is LoadDetailResult -> {
                            vs.copy(isLoading = false, error = result.packet.error)
                        }
                    }
                }
                is Lce.Loading -> {
                    vs.copy(isLoading = true)
                }
            }
        }
    }

    private fun Observable<DetailViewEvent>.eventToResult() : Observable<Lce<out DetailViewResult>> {
        return publish {
            it.ofType(LoadDetailEvent::class.java).onLoadDetails()
        }
    }

    private fun Observable<LoadDetailEvent>.onLoadDetails() : Observable<Lce<out DetailViewResult>> {
        return switchMap {
            detailRepository.getDetails(it.newsId, it.newsType)
        }
    }
}