package com.yasin.okcredit.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasin.okcredit.MOVIES_NEWS
import com.yasin.okcredit.OkCredit
import com.yasin.okcredit.R
import com.yasin.okcredit.dagger.modules.ViewModelFactory
import com.yasin.okcredit.ui.movies.MoviesViewEvent.ScreenLoadEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_movies.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 17/2/20.
 */
class MoviesFragment : Fragment() {

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var uiDisposable : Disposable
    private lateinit var disposable: Disposable
    private val newsAdapter : MoviesAdapter by lazy { MoviesAdapter(requireContext(), MOVIES_NEWS) }
    private val swipeRefesh : PublishSubject<ScreenLoadEvent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        OkCredit.getApp(requireContext()).mainComponent.injectMovies(this)
        super.onCreate(savedInstanceState)
        configureViewModel()
        observeViewState()
    }

    private fun configureViewModel() {
        movieViewModel = ViewModelProvider(requireActivity(),viewmodelFactory).get(MovieViewModel::class.java)
    }

    private fun observeViewState() {
        disposable = movieViewModel.viewState
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
        return inflater.inflate(R.layout.fragment_movies,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insetWindow()
        init()
    }

    private fun insetWindow() {
        cl_movies.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        cl_movies.setOnApplyWindowInsetsListener { v, insets ->

            rv_movies.setPadding(0,insets.systemWindowInsetTop,0,0)

            // clear this listener so insets aren't re-applied
            cl_movies.setOnApplyWindowInsetsListener(null)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun init() {
        rv_movies.adapter = newsAdapter
        swipe_refresh_movies.setOnRefreshListener(onRefreshListener)
        val screenLoadEvent : Observable<ScreenLoadEvent> = swipeRefesh
        uiDisposable = screenLoadEvent.subscribe(
            {
                movieViewModel.processInput(it)
            },{
                Timber.e(it, "error processing input")
            }
        )
        swipeRefesh.onNext(ScreenLoadEvent) // first time
    }

    private fun renderViewState(it: MoviesViewState?) {
        swipe_refresh_movies.isRefreshing = it?.isLoading ?: false
        if(it?.isEmpty == true){ // news is empty only when sth is wrong
            rv_movies.visibility = View.INVISIBLE
            empty_view.visibility = View.VISIBLE
        }else{
            empty_view.visibility = View.GONE
            rv_movies.visibility = View.VISIBLE
            newsAdapter.submitList(it?.adapterList)
        }
        it?.error.let {
            if(!it.isNullOrEmpty()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefesh.onNext(ScreenLoadEvent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDisposable.dispose()
    }
}