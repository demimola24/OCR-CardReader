//package com.ajocardreader.models.apimodels
//
//import java.net.HttpURLConnection
//import retrofit2.Response
//
//data class ApiResponse<T> (
//
//    var success: Boolean,
//
//    var status: String,
//
//    var message: String?,
//
//    var result: T?
//
//){
//    companion object {
//
//        fun <T> processApiResponse(apiResponseResponse: Response<out ApiResponse<T>>?): ApiResponse<*>? {
//            if (apiResponseResponse == null) {
//                return ApiResponse.createErrorResponse("Unable to retrieve details")
//            }
//
//            val responseCode = apiResponseResponse.code()
//
//            if (responseCode != HttpURLConnection.HTTP_OK) {
//                return extractErrorBody(apiResponseResponse)
//            }
//
//            return if (apiResponseResponse.body() == null) {
//                ApiResponse.createErrorResponse("Unable to retrieve details")
//            } else apiResponseResponse.body()
//
//        }
//
//        @JvmOverloads
//        fun createErrorResponse(errorMessage: String?, statusCode: Int = 0): ApiResponse<*> {
//            lateinit var response : ApiResponse<Any>
//            response.message = errorMessage
//            response.success = false
//            response.status = statusCode.toString()
//            return response
//        }
//
//
//        fun createSuccessResponse(result: Any): ApiResponse<*> {
//            lateinit var response : ApiResponse<Any>
//            response.result = result
//            response.success = true
//            return response
//        }
//
//        private fun extractErrorBody(response: Response<*>): ApiResponse<*> {
//            val responseCode = response.code()
//            return try {
//                if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
//                    createErrorResponse("Unknown card, please check and try again!")
//                } else {
//                    createErrorResponse("Unknown card, please check and try again!", responseCode)
//                }
//
//            } catch (ex: Exception) {
//                createErrorResponse(ex.message, responseCode)
//            }
//
//        }
//    }
//}
