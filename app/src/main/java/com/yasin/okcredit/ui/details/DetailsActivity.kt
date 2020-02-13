package com.yasin.okcredit.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.yasin.okcredit.*
import com.yasin.okcredit.dagger.modules.ViewModelFactory
import com.yasin.okcredit.ui.details.DetailViewEffect.*
import com.yasin.okcredit.ui.details.DetailViewEvent.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_details.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 12/2/20.
 */
class DetailsActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var detailViewModel : DetailViewModel
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private lateinit var uiDisposable: Disposable
    private val openLinkEvent : PublishSubject<String> = PublishSubject.create()

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
        applyWindowInsets()
        configureViewModel()
        init()
        attachStateObserver()
    }

    private fun applyWindowInsets() {
        coordinator.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        coordinator.setOnApplyWindowInsetsListener { v, insets ->

            val lpCloseButton = back_button?.layoutParams as ViewGroup.MarginLayoutParams
            lpCloseButton.topMargin += insets.systemWindowInsetTop
            back_button.layoutParams = lpCloseButton

            // clear this listener so insets aren't re-applied
            coordinator.setOnApplyWindowInsetsListener(null)
            insets.consumeSystemWindowInsets()
        }

    }

    override fun onResume() {
        super.onResume()
        val loadDetailEvent = Observable.just(LoadDetailEvent(
            intent.getStringExtra(NEWS_ID),
            intent.getStringExtra(NEWS_TYPE)
        ))
        val openLinkEvent : Observable<OpenChromeEvent> = openLinkEvent
            .map { OpenChromeEvent(it) }
        uiDisposable =
            Observable.merge (
                openLinkEvent,
                loadDetailEvent
            ).subscribe({
                detailViewModel.processInput(it)
            }, {
                Timber.e(it, "error processing input")
            })
    }

    private fun configureViewModel() {
        detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private fun attachStateObserver() {
        disposable.add(
            detailViewModel.viewState
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    renderViewState(it)
                }, {
                    error("Something terribly wrong happened when set view state")
                })
        )
        disposable.add(
            detailViewModel.viewEffects
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    trigger(it)
                }, {
                    error("Something terribly wrong happened when set view state")
                })
        )
    }

    private fun trigger(effect: DetailViewEffect?) {
        effect ?: return
        when(effect) {
            is OpenChromeEffect -> {
                openChrome(effect.uri) {
                    Toast.makeText(this, "error opening Browser!", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
        published.text = String.format("Published on",it?.dateToFormat(it.published ?: ""))
    }

    private fun init() {
        tv_link.setOnClickListener {
            openLinkEvent.onNext(tv_link.text.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        uiDisposable.dispose()
        disposable.dispose()
    }
}