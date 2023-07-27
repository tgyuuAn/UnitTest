package com.example.unittest.other

import retrofit2.Response

inline fun <reified T : Any> Response<T>.onResponse(): Result<T> {
    return if (isSuccessful) {
        body()?.let { body ->
            Result.success(body)
        } ?: Result.failure(Throwable("Response is Null"))
    } else {
        Result.failure(errorBody()?.string()?.let { Throwable(it) } ?: Throwable("Response Error"))
    }
}