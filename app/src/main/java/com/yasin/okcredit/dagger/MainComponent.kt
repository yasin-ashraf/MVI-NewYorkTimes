package com.yasin.okcredit.dagger

import com.yasin.okcredit.dagger.modules.ApplicationModule
import com.yasin.okcredit.dagger.modules.ContextModule
import com.yasin.okcredit.dagger.modules.DatabaseModule
import com.yasin.okcredit.dagger.modules.ViewModelModule
import com.yasin.okcredit.dagger.scope.ApplicationScope
import com.yasin.okcredit.ui.details.DetailsActivity
import com.yasin.okcredit.ui.home.HomeFragment
import com.yasin.okcredit.ui.movies.MoviesFragment
import com.yasin.okcredit.ui.science.ScienceFragment
import com.yasin.okcredit.ui.sports.SportsFragment
import dagger.Component

/**
 * Created by Yasin on 4/2/20.
 */
@ApplicationScope
@Component(modules = [ContextModule::class, ApplicationModule::class, DatabaseModule::class, ViewModelModule::class])
interface MainComponent {
    fun injectHome(homeFragment: HomeFragment)
    fun injectDetails(detailsActivity: DetailsActivity)
    fun injectMovies(moviesFragment: MoviesFragment)
    fun injectScience(scienceFragment: ScienceFragment)
    fun injectSports(sportsFragment: SportsFragment)


}