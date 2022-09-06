package com.github.royalfamily.vagan.data.remote

import com.github.royalfamily.vagan.data.Resource
import com.github.royalfamily.vagan.dto.AuthToken
import retrofit2.Response

interface VeganDataSource {

    suspend fun requestUserAuth(body: AuthToken.Request) : Resource<AuthToken.Response>

}
