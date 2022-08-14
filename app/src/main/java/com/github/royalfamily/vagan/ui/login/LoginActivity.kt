package com.github.royalfamily.vagan.ui.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityLoginBinding
import com.github.royalfamily.vagan.ui.base.BaseActivity
import com.kakao.sdk.auth.model.OAuthToken
import androidx.activity.viewModels
import com.kakao.sdk.user.UserApiClient
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
    }

    private fun loginWithKakaoTalk() {
        // 우선 카카오 앱으로 로그인을 시도하고, 실패하면 kakao 계정 로그인을 호출한다.
        UserApiClient.instance
            .loginWithKakaoTalk(this) { oAuthToken: OAuthToken?, error: Throwable? ->
                if (error != null) {
                    loginWithKakaoAcount()
                } else if (oAuthToken != null) {
                    showToast("로그인 성공(토큰) : " + oAuthToken.accessToken)
                }
            }
    }

    private fun loginWithKakaoAcount() {
        UserApiClient.instance
            .loginWithKakaoAccount(this) { oAuthToken: OAuthToken?, error: Throwable? ->
                if (error != null) {
                    showToast("로그인 실패 $error")
                } else if (oAuthToken != null) {
                    showToast("로그인 성공(토큰) :  ${oAuthToken.accessToken}")
                }
            }
    }
    
}