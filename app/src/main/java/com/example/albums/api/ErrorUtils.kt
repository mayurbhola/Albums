package com.example.albums.api

import retrofit2.Response
import retrofit2.Retrofit

object ErrorUtils {

    fun parseError(response: Response<*>, retrofit: Retrofit): ErrorResponse? {
        val converter = retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            arrayOfNulls(0)
        )
        return try {
            if (response.errorBody() != null) {
                response.errorBody()?.let {
                    converter.convert(it)
                }
            } else {
                ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            ErrorResponse(response.code(), e.message)
        }
    }
}