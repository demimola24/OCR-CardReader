package com.ajocardreader.config


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardReaderViewModelFactory(private val cardReaderApplication: CardReaderApplication) :
    ViewModelProvider.NewInstanceFactory() {

    internal var t: ViewModel? = null

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (t == null) {
            t = super.create(modelClass)
            if (t is CardReaderDaggerComponent.Injectable) {
                (t as CardReaderDaggerComponent.Injectable).inject(cardReaderApplication.daggerComponent)
            }
        }
        return t as T
    }
}
