package com.yasin.okcredit.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.yasin.okcredit.NEWS_ID
import com.yasin.okcredit.NEWS_TYPE
import com.yasin.okcredit.OkCredit
import com.yasin.okcredit.R
import com.yasin.okcredit.dagger.modules.ViewModelFactory
import com.yasin.okcredit.ui.details.DetailViewEvent.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_details.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 12/2/20.
 */
class DetailsActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var disposable: Disposable
    private lateinit var uiDisposable : Disposable

    companion object {
        fun getNewIntent(id: String, context: Context, type: String) : Intent{
            val newIntent = Intent(context, DetailsActivity::class.java)
            newIntent.putExtra(NEWS_ID, id)
            newIntent.putExtra(NEWS_TYPE, type)
            return newIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        OkCredit.getApp(this).mainComponent.injectDetails(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        configureViewModel()
        attachViewStateObserver()
    }

    override fun onResume() {
        super.onResume()
        val loadDetailEvent = Observable.just(LoadDetailEvent(
            intent.getStringExtra(NEWS_ID),
            intent.getStringExtra(NEWS_TYPE)
        ))
        uiDisposable =
            loadDetailEvent.subscribe({
                detailViewModel.processInput(it)
            }, {
                Timber.e(it, "error processing input")
            })
    }

    private fun configureViewModel() {
        detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private fun attachViewStateObserver() {
        disposable = detailViewModel.viewState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                renderViewState(it)
            }, {
                error("Something terribly wrong happened when set view state")
            })
    }

    private fun renderViewState(it: DetailViewState?) {
        if(it?.isLoading == true) progress.visibility = View.VISIBLE
        else progress.visibility = View.INVISIBLE
        if(!it?.coverPhoto.isNullOrEmpty()){
            Picasso.get()
                .load(it?.coverPhoto)
                .fit()
                .centerCrop()
                .into(iv_cover)
        }
        tv_title.text = it?.title
        tv_abstract.text = it?.abstract
        author.text = it?.author
        tv_link.text = it?.link
    }

    override fun onDestroy()  {
        super.onDestroy()
        disposable.dispose()
    }

}