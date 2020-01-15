package com.ajocardreader.datasource

import com.ajocardreader.models.CardVerificationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


 interface CardReaderApiService {

    /**
     * get Card Details
     * */
    @GET("{cardNumber}")
    open fun getCardDetails(@Path("cardNumber") cardNumber: String): Call<CardVerificationResponse>

}