package com.github.royalfamily.vagan.data.remote

import com.github.royalfamily.vagan.data.BaseDataSource
import com.github.royalfamily.vagan.data.Resource
import com.github.royalfamily.vagan.data.service.VeganService
import com.github.royalfamily.vagan.dto.AuthToken
import javax.inject.Inject
import javax.inject.Named

class VeganDataSourceImpl @Inject constructor(
    private val veganService: VeganService
) : VeganDataSource, BaseDataSource() {

    override suspend fun requestUserAuth(body: AuthToken.Request): Resource<AuthToken.Response> {
        return getResponse(veganService.requestUserAuth(body))
    }

}