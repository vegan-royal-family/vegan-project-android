package com.github.royalfamily.vagan.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityLoginBinding
import com.github.royalfamily.vagan.ui.base.BaseActivity
import com.kakao.sdk.auth.model.OAuthToken
import androidx.activity.viewModels
import com.github.royalfamily.vagan.data.Resource
import com.github.royalfamily.vagan.enum.LoginType
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setListener() {

        super.setListener()

        // Databinding을 해주었기 때문에, 위에서 선언한 binding 키워드로 xml의 view에 findViewById 없이 접근할 수 있어요.
        // xml에선 snake 표기법을 사용하는데, binding에서 id를 camel 표기법으로 변환하기때문에 btnRequest로 접근합니다.
        binding.btnKakao.setOnClickListener {
            loginWithKakaoTalk()
        }

        binding.btnNaver.setOnClickListener {
            loginWithNaverAccount()
        }

    }

    override fun setObserver() {

        viewModel.requestToken.observe(this) {
            Log.d(
                "API_RESPONSE",
                "${it.status}, ${it.statusCode}, ${it.data?.isNew}, ${it.message}"
            )
        }

    }

    private fun loginWithKakaoTalk() {
        // 우선 카카오 앱으로 로그인을 시도하고, 실패하면 kakao 계정 로그인을 호출한다.
        UserApiClient.instance.loginWithKakaoTalk(this) { oAuthToken: OAuthToken?, error: Throwable? ->
            if (error != null) {
                loginWithKakaoAcount()
            } else if (oAuthToken != null) {
                viewModel.requestToken(
                    LoginType.KAKAO,
                    oAuthToken.accessToken
                )
            }
        }

    }

    private fun loginWithKakaoAcount() {

        UserApiClient.instance.loginWithKakaoAccount(this) { oAuthToken: OAuthToken?, error: Throwable? ->
            if (error != null) {
                showToast("로그인 실패 $error")
            } else if (oAuthToken != null) {
                viewModel.requestToken(
                    LoginType.KAKAO,
                    oAuthToken.accessToken
                )
            }
        }

    }

    private fun loginWithNaverAccount() {

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {

                NaverIdLoginSDK.getAccessToken()?.let { accessToken ->
                    viewModel.requestToken(
                        LoginType.NAVER,
                        accessToken
                    )
                }

            }

            override fun onFailure(httpStatus: Int, message: String) {

                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()

            }

            override fun onError(errorCode: Int, message: String) {

                onFailure(errorCode, message)

            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)

    }

}