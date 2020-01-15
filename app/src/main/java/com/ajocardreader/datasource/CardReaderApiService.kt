package com.ajocardreader.datasource

import CardVerificationResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface CardReaderApiService {

    /**
     * get Card Details
     * */
    @GET("{cardNumber}")
    fun getCardDetails(@Path("cardNumber") cardNumber: String): Call<CardVerificationResponse>

}