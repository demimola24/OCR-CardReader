package com.ajocardreader.datasource.repository

import CardVerificationResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.ajocardreader.datasource.CardReaderApiService
import com.ajocardreader.datasource.RetrofitRequest
import io.reactivex.Single

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ArticleRepository {

    private val cardReaderApiService: CardReaderApiService

    init {
        cardReaderApiService = RetrofitRequest.retrofitInstance!!.create<CardReaderApiService>(CardReaderApiService::class.java!!)
    }


    fun getCardDetails(cardNumber: String): Single<CardVerificationResponse> {
        return Single.create<CardVerificationResponse> { emitter ->
            cardReaderApiService.getCardDetails(cardNumber)
                .enqueue(object : Callback<CardVerificationResponse> {
                    override fun onResponse(
                        call: Call<CardVerificationResponse>,
                        response: Response<CardVerificationResponse>
                    ) {
                        response.body()?.let {
                            emitter.onSuccess(it)
                        }
                    }

                    override fun onFailure(call: Call<CardVerificationResponse>, t: Throwable) {
                        emitter.onError(t)
                    }
                })
        }
    }

    companion object {
        private val TAG = ArticleRepository::class.java.simpleName
    }
}