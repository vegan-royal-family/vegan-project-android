package com.github.royalfamily.vagan.dto

import com.google.gson.annotations.SerializedName

data class AuthToken(
    val request: Request,
    val response: Response?
) {
    data class Request(
        @SerializedName("provider")
        val loginType: String,
        @SerializedName("token")
        val accessToken: String,
    )

    data class Response(
        @SerializedName("isNew")
        val isNew: Boolean
    )
}