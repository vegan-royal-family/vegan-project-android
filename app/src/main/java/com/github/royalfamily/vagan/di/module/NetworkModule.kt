package com.github.royalfamily.vagan.di.module

import com.github.royalfamily.vagan.data.Repository
import com.github.royalfamily.vagan.data.RepositoryImpl
import com.github.royalfamily.vagan.data.remote.VeganDataSource
import com.github.royalfamily.vagan.data.remote.VeganDataSourceImpl
import com.github.royalfamily.vagan.data.service.VeganService
import com.github.royalfamily.vagan.util.NETWORK_BASE_NAME_RETROFIT
import com.github.royalfamily.vagan.util.NETWORK_BASE_NAME_SERVICE
import com.github.royalfamily.vagan.util.NETWORK_BASE_NAME_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import javax.inject.Named
import javax.inject.Singleton

/*
    @Module
    생상자 주입이 불가능하기 때문에 직접 Module을 만들어주고, Hilt에게 이 클래스가 모듈임을 알린다.

    @InstallIn(SingletonComponent::class)
    Application이 살아있는 동안 유지할 컴포넌트로 선언하기 위한 어노테이션 입니다.

    @Provides
    DI 주입 시 생성할 오브젝트를 만들기 위한 Function에 사용합니다.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named(NETWORK_BASE_NAME_URL)
    fun provideBaseUrl() = "https://eeelw22u70.execute-api.ap-northeast-2.amazonaws.com/"

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .addInterceptor(logginInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named(NETWORK_BASE_NAME_RETROFIT)
    fun provideBaseRetrofit(
        okHttpClient: OkHttpClient, @Named(NETWORK_BASE_NAME_URL) baseUrl: String
    ) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkService(@Named(NETWORK_BASE_NAME_RETROFIT) retrofit: Retrofit) : VeganService {
        return retrofit.create(VeganService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: VeganService) : VeganDataSource {
        return VeganDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideRepository(veganDataSource: VeganDataSource) : Repository {
        return RepositoryImpl(veganDataSource)
    }

}