package com.ajocardreader.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajocardreader.viewmodel.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @auto jerry on 29/07/2018
 * for VelaBank
 **/

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(authViewModel: MainActivityViewModel): ViewModel

    /**
     * Provides the MyViewModelFactory
     * */
    @Binds
    abstract fun provideCardReaderViewModelFactory(factory: CardReaderViewModelFactory): ViewModelProvider.Factory
}