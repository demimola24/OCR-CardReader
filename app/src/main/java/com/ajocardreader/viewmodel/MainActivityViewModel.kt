package com.ajocardreader.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajocardreader.config.CardReaderDaggerComponent
import com.ajocardreader.datasource.repository.Repository
import com.ajocardreader.models.CardVerificationResponse
import io.reactivex.functions.Consumer
import javax.inject.Inject


open class MainActivityViewModel
@Inject internal constructor(val repository: Repository) : ViewModel(), CardReaderDaggerComponent.Injectable
{

     val cardVerificationDetailEvent = MutableLiveData<CardVerificationResponse>()
     val progressEvent = MutableLiveData<Boolean>()
     val errorEvent = MutableLiveData<String>()

    fun getCardVerificationEvent() = cardVerificationDetailEvent

    fun getProgress() = progressEvent

    fun getErrorMessageEvent() = errorEvent


    override
    fun inject(cardReaderDaggerComponent: CardReaderDaggerComponent) {
        cardReaderDaggerComponent.inject(this)
    }


    @SuppressLint("CheckResult")
    open fun getCardDetails(cardDetails: String) {
        repository.getCardDetails(cardDetails)
            .subscribe{ businessApiResponse,throwable ->
                if (throwable != null) {
                    errorEvent.postValue(throwable.message)
                    return@subscribe
                }
                cardVerificationDetailEvent.postValue(businessApiResponse)
            }

    }

}
