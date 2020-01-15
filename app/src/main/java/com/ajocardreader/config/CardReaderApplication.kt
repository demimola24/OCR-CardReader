package com.ajocardreader.config

import android.app.Application

class CardReaderApplication : Application() {

    lateinit var daggerComponent: CardReaderDaggerComponent

    lateinit var cardReaderViewModelFactory: CardReaderViewModelFactory


    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerCardReaderDaggerComponent.builder()
            .cardReaderModule(CardReaderModule(this))
            .build()

        cardReaderViewModelFactory = CardReaderViewModelFactory(this)
    }
}
