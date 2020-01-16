package com.ajocardreader.datasource.repository

import android.util.Log
import com.ajocardreader.models.CardVerificationResponse
import com.ajocardreader.datasource.CardReaderApiService
import io.reactivex.Single

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.inject.Inject

open class Repository
@Inject constructor(private val cardReaderApiService: CardReaderApiService) {

    open fun getCardDetails(cardNumber: String): Single<CardVerificationResponse> {
        return Single.create<CardVerificationResponse> { emitter ->
            cardReaderApiService.getCardDetails(cardNumber)
                .enqueue(object : Callback<CardVerificationResponse> {
                    override fun onResponse(
                        call: Call<CardVerificationResponse>,
                        response: Response<CardVerificationResponse>
                    ) {
                        Log.d("CardVerification","CardVerificationResponse: $response")
                        if(response.isSuccessful){
                            emitter.onSuccess(response.body()!!)
                        }else if (response.code()==404){
                            emitter.onError(Throwable("No card found"))
                        }else{
                            emitter.onError(Throwable("Unable to retrieve card details"))
                        }

                    }

                    override fun onFailure(
                        call: Call<CardVerificationResponse>,
                        t: Throwable
                    ) {
                        Log.d("CardVerification","CardVerificationError: ${t.printStackTrace()}")
                        emitter.onError(t)
                    }

                })
        }
    }
}