package com.ajocardreader.models.apimodels;

import java.net.HttpURLConnection;
import retrofit2.Response;

public class ApiResponse<T> {

    private boolean success;

    private String status;

    private String message;

    private T result;

    public static <T> ApiResponse processApiResponse(Response<? extends ApiResponse<T>> apiResponseResponse) {
        if (apiResponseResponse == null) {
            return ApiResponse.createErrorResponse("Unable to retrieve details");
        }

        int responseCode = apiResponseResponse.code();

        if (!(responseCode == HttpURLConnection.HTTP_OK)) {
            return extractErrorBody(apiResponseResponse);
        }

        if (apiResponseResponse.body() == null) {
            return ApiResponse.createErrorResponse("Unable to retrieve details");
        }

        return apiResponseResponse.body();
    }

    public static ApiResponse createErrorResponse(String errorMessage) {
        return createErrorResponse(errorMessage, 0);
    }

    public static ApiResponse createErrorResponse(String errorMessage, int statusCode) {
        ApiResponse response = new ApiResponse();
        response.message= errorMessage;
        response.success = false;
        response.status = String.valueOf(statusCode);
        return response;
    }


    public static ApiResponse createSuccessResponse(Object result) {
        ApiResponse response = new ApiResponse();
        response.result = result;
        response.success = true ;
        return response;
    }

    private static ApiResponse extractErrorBody(Response response) {
        int responseCode = response.code();
        try {
            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                return createErrorResponse("Unknown card, please check and try again!");
            }else{
                return createErrorResponse("Unknown card, please check and try again!", responseCode);
            }

        } catch (Exception ex) {
            return createErrorResponse(ex.getMessage(), responseCode);
        }
    }
}
