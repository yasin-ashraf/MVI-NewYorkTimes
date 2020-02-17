package com.yasin.okcredit.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasin.okcredit.ui.details.DetailViewModel
import com.yasin.okcredit.ui.home.HomeViewModel
import com.yasin.okcredit.ui.movies.MovieViewModel
import com.yasin.okcredit.ui.science.ScienceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Yasin on 4/2/20.
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMoviesViewModel(movieViewModel: MovieViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScienceViewModel::class)
    abstract fun bindScienceViewModel(movieViewModel: ScienceViewModel) : ViewModel

}