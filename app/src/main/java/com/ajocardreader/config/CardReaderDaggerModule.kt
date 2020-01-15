package com.ajocardreader.config

import android.app.Application
import android.content.Context
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class CardReaderDaggerModule(private val application: Application) {

    @Singleton
    @Provides
     fun provideApplication(): Application {
        return application
    }

//    companion object {
//
//        @Provides
//        @Singleton
//        internal fun provideContext(application: Application): Context {
//            return application.applicationContext
//        }
//    }
}
