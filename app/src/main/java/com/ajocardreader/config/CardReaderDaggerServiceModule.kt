package com.ajocardreader.config

import android.app.Application

import com.ajocardreader.datasource.CardReaderApiService
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object CardReaderDaggerServiceModule {

    @Provides
    @Singleton
     fun provideCardReaderApiService(retrofitInstance: Retrofit): CardReaderApiService {
        return retrofitInstance.create(CardReaderApiService::class.java)
    }

}
