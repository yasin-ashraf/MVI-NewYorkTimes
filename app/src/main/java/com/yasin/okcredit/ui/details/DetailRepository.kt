package com.yasin.okcredit.ui.details

import com.yasin.okcredit.data.repository.LocalRepository
import com.yasin.okcredit.network.Lce
import com.yasin.okcredit.viewState.DetailViewResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yasin on 12/2/20.
 */
class DetailRepository @Inject constructor(private val localRepository: LocalRepository) {

    fun getDetails(id: String, type : String): Observable<Lce<DetailViewResult.LoadDetailResult>> {
        return                 localRepository.getNewsDetails(id)
            .subscribeOn(Schedulers.io())
            .map {
                if(it.id == id){
                    Lce.Content(
                        DetailViewResult.LoadDetailResult(
                        GeneralNews(
                            title = it.title,
                            author = it.author,
                            thumbnail = it.thumbnail,
                            abstract = it.abstractSt,
                            coverImage = it.coverImage,
                            articleLink = it.articleLink,
                            publishedOn = it.publishedDate
                        )
                    ))
                }else {
                    Lce.Error(
                        DetailViewResult.LoadDetailResult(
                        GeneralNews(),error = "error !! Unable to fetch details."
                    ))
                }
            }.startWith(Lce.Loading())
    }
}