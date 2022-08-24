package com.github.royalfamily.vagan

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp


/*
     Application 클래스는 기본 구성 요소를 포함하는 안드로이드 앱 내의 기본 클래스다.
     Application 클래스는 앱에 대한 프로세스가 생성될때 가장 먼저 먼저 인스턴스화된다.
     이 클래스는 주로 첫 번째 액티비티가 표시되기 전에 전역 상태를 초기화하는 데 사용된다.
     Application 클래스를 만들고 Application 클래스를 상속한 뒤,
     매니페스트의 android:name 속성에 등록해서 사용한다.
 */

/*
    @HiltAndroidApp : Hilt Application Class 지정
    Hilt를 사용하는 모든 앱은 @HiltAndroidApp으로 주석이 지정된 Application 클래스를 포함해야 한다.
 */

@HiltAndroidApp
class Application : Application() {

    companion object {
        var context : Context? = null
        lateinit var mGoogleSignInClient : GoogleSignInClient
    }
    override fun onCreate() {
        super.onCreate()
        context = this
        KakaoSdk.init(this,getString(R.string.kakao_app_key))

        val naverClientId = getString(R.string.naver_client_id)
        val naverClientSecret = getString(R.string.naver_client_secret)
        val naverClientName = getString(R.string.naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



    }

}