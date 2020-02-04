package com.yasin.okcredit.dagger

import com.yasin.okcredit.dagger.modules.ApplicationModule
import com.yasin.okcredit.dagger.modules.ContextModule
import com.yasin.okcredit.dagger.modules.DatabaseModule
import com.yasin.okcredit.dagger.modules.ViewModelModule
import com.yasin.okcredit.dagger.scope.ApplicationScope
import dagger.Component

/**
 * Created by Yasin on 4/2/20.
 */
@ApplicationScope
@Component(modules = [ContextModule::class, ApplicationModule::class, DatabaseModule::class, ViewModelModule::class])
interface MainComponent {


}