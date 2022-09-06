package com.github.royalfamily.vagan.data

import com.github.royalfamily.vagan.data.remote.VeganDataSource
import com.github.royalfamily.vagan.dto.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val veganRemoteDataSource : VeganDataSource
) : Repository {

    override suspend fun requestUserAuth(body: AuthToken.Request): Flow<Resource<AuthToken.Response>> {
        return flow {
            emit(Resource.loading())
            emit(veganRemoteDataSource.requestUserAuth(body))
        }
    }

}