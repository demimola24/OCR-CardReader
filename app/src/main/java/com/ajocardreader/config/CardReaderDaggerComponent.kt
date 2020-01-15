package com.ajocardreader.config


import com.ajocardreader.ui.MainActivity
import com.ajocardreader.viewmodel.MainActivityViewModel

import javax.inject.Singleton

import dagger.Component

//CardReader

@Singleton
@Component(modules = [CardReaderDaggerModule::class, CardReaderDaggerServiceModule::class, CardReaderRetrofitModule::class])
interface CardReaderDaggerComponent {

    /* Activities */
    fun inject(launcherActivity: MainActivity)

    /* MainActivityViewModel */
    fun inject(mainActivityViewModel: MainActivityViewModel)


    interface Injectable {
        fun inject(cardReaderDaggerComponent: CardReaderDaggerComponent)
    }
}
