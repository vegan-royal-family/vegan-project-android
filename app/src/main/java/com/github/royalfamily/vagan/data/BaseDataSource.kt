package com.github.royalfamily.vagan.data

import retrofit2.Response

open class BaseDataSource {

    internal fun <T> getResponse(response: Response<T>): Resource<T> {
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Resource.success(body)
                } ?: Resource.error(response.code(), "data is null")
            } else {
                Resource.error(response.code(), response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(response.code(), e.message ?: e.toString())
        }
    }

}

data class Resource<out T>(
    val status: Status,
    val statusCode: Int = -1,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(statusCode: Int, message: String): Resource<T> {
            return Resource(
                status = Status.ERROR,
                statusCode = statusCode,
                message = message
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                status = Status.LOADING
            )
        }
    }
}