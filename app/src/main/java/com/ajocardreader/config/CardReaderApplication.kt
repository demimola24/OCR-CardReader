package com.ajocardreader.config

import android.app.Application

class CardReaderApplication : Application() {

    lateinit var daggerComponent: CardReaderDaggerComponent

    lateinit var cardReaderViewModelFactory: CardReaderViewModelFactory


    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerCardReaderDaggerComponent.builder()
            .cardReaderDaggerModule(CardReaderDaggerModule(this))
            .build()

        cardReaderViewModelFactory = CardReaderViewModelFactory(this)
    }
}
