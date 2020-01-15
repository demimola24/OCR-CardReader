package com.ajocardreader.datasource.repository

import CardVerificationResponse
import com.ajocardreader.datasource.CardReaderApiService
import com.ajocardreader.models.apimodels.ApiResponse
import io.reactivex.Single

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.inject.Inject

class Repository {

    private lateinit var cardReaderApiService: CardReaderApiService

    @Inject
    fun Repository(cardReaderApiService: CardReaderApiService){
        this.cardReaderApiService = cardReaderApiService
    }


    fun getCardDetails(cardNumber: String): Single<ApiResponse<CardVerificationResponse>> {
        return Single.create<ApiResponse<CardVerificationResponse>> { emitter ->
            cardReaderApiService.getCardDetails(cardNumber)
                .enqueue(object : Callback<ApiResponse<CardVerificationResponse>> {
                    override fun onResponse(
                        call: Call<ApiResponse<CardVerificationResponse>>,
                        response: Response<ApiResponse<CardVerificationResponse>>
                    ) {
                            emitter.onSuccess(ApiResponse.processApiResponse(response) as ApiResponse<CardVerificationResponse>)

                    }

                    override fun onFailure(
                        call: Call<ApiResponse<CardVerificationResponse>>,
                        t: Throwable
                    ) {
                        emitter.onSuccess(ApiResponse.createErrorResponse(t.message) as ApiResponse<CardVerificationResponse>)
                    }

                })
        }
    }
}