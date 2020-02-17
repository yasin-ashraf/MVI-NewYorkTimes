package com.yasin.okcredit.ui.science

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasin.okcredit.OkCredit
import com.yasin.okcredit.R
import com.yasin.okcredit.SCIENCE_NEWS
import com.yasin.okcredit.dagger.modules.ViewModelFactory
import com.yasin.okcredit.ui.adapter.NewsAdapter
import com.yasin.okcredit.viewState.NewsViewEvent
import com.yasin.okcredit.viewState.NewsViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_science.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 5/2/20.
 */
class ScienceFragment : Fragment() {

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    private lateinit var scienceViewModel: ScienceViewModel
    private lateinit var uiDisposable : Disposable
    private lateinit var disposable: Disposable
    private val newsAdapter : NewsAdapter by lazy { NewsAdapter(requireContext(), SCIENCE_NEWS) }
    private val swipeRefesh : PublishSubject<NewsViewEvent.ScreenLoadEvent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        OkCredit.getApp(requireContext()).mainComponent.injectScience(this)
        super.onCreate(savedInstanceState)
        configureViewModel()
        observeViewState()
    }

    private fun configureViewModel() {
        scienceViewModel = ViewModelProvider(requireActivity(),viewmodelFactory).get(ScienceViewModel::class.java)
    }

    private fun observeViewState() {
        disposable = scienceViewModel.viewState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                renderViewState(it)
            }, { Timber.e(it, "something went terribly wrong processing view state") })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_science,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insetWindow()
        init()
    }

    private fun insetWindow() {
        cl_science.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        cl_science.setOnApplyWindowInsetsListener { v, insets ->

            rv_science.setPadding(0,insets.systemWindowInsetTop,0,0)

            // clear this listener so insets aren't re-applied
            cl_science.setOnApplyWindowInsetsListener(null)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun init() {
        rv_science.adapter = newsAdapter
        swipe_refresh_science.setOnRefreshListener(onRefreshListener)
        val screenLoadEvent : Observable<NewsViewEvent.ScreenLoadEvent> = swipeRefesh
        uiDisposable = screenLoadEvent.subscribe(
            {
                scienceViewModel.processInput(it)
            },{
                Timber.e(it, "error processing input")
            }
        )
        swipeRefesh.onNext(NewsViewEvent.ScreenLoadEvent) // first time
    }

    private fun renderViewState(it: NewsViewState?) {
        swipe_refresh_science.isRefreshing = it?.isLoading ?: false
        if(it?.isEmpty == true){ // news is empty only when sth is wrong
            rv_science.visibility = View.INVISIBLE
            empty_view.visibility = View.VISIBLE
        }else{
            empty_view.visibility = View.GONE
            rv_science.visibility = View.VISIBLE
            newsAdapter.submitList(it?.adapterList)
        }
        it?.error.let {
            if(!it.isNullOrEmpty()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefesh.onNext(NewsViewEvent.ScreenLoadEvent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDisposable.dispose()
    }
}