package com.github.royalfamily.vagan.data

import com.github.royalfamily.vagan.dto.AuthToken
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun requestUserAuth(body: AuthToken.Request) : Flow<Resource<AuthToken.Response>>

}