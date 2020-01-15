package com.ajocardreader.viewmodel

import CardVerificationResponse
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajocardreader.config.CardReaderDaggerComponent
import com.ajocardreader.datasource.repository.Repository
import io.reactivex.functions.Consumer
import java.io.Serializable
import javax.inject.Inject


class MainActivityViewModel : ViewModel(), Serializable, CardReaderDaggerComponent.Injectable {

    //private val cardVerificationEvent = MutableLiveData<CardVerificationResponse>()

    @Inject
    var repository: Repository? = null


    override 
    fun inject(cardReaderDaggerComponent: CardReaderDaggerComponent) {
        cardReaderDaggerComponent.inject(this)
    }


    @SuppressLint("CheckResult")
    fun getCardDetails(cardDetails: String): LiveData<CardVerificationResponse> {
        val responseLiveData = MutableLiveData<CardVerificationResponse>()
        repository?.getCardDetails(cardDetails)
            ?.subscribe(Consumer<CardVerificationResponse> { businessApiResponse ->
                responseLiveData.postValue(
                    businessApiResponse
                )
            })

        return responseLiveData
    }

}
