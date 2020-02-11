package com.yasin.okcredit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasin.okcredit.OkCredit
import com.yasin.okcredit.R
import com.yasin.okcredit.dagger.modules.ViewModelFactory
import com.yasin.okcredit.ui.home.HomeViewEvent.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yasin on 5/2/20.
 */
class HomeFragment : Fragment() {

    @Inject lateinit var viewmodelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var uiDisposable : Disposable
    private val swipeRefesh : PublishSubject<ScreenReloadEvent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        OkCredit.getApp(requireContext()).mainComponent.injectHome(this)
        super.onCreate(savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        homeViewModel = ViewModelProvider(requireActivity(),viewmodelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        swipe_refresh_home.setOnRefreshListener(onRefreshListener)
        val screenLoadEvent : Observable<ScreenLoadEvent> = Observable.just(ScreenLoadEvent)
        val screenReloadEvent : Observable<ScreenReloadEvent> = swipeRefesh
        uiDisposable = Observable.merge(
            screenLoadEvent,
            screenReloadEvent
        ).subscribe(
            {
                homeViewModel.processInput(it)
            },{
                Timber.e(it, "error processing input")
            }
        )
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefesh.onNext(ScreenReloadEvent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDisposable.dispose()
    }
}