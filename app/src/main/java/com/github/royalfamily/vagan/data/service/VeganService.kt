package com.github.royalfamily.vagan.data.service

import com.github.royalfamily.vagan.dto.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface VeganService {

    @POST("/auth/token")
    suspend fun requestUserAuth(
        @Body body: AuthToken.Request
    ) : Response<AuthToken.Response>

}