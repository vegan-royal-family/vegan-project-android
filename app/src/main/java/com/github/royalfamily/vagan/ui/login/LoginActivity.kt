package com.github.royalfamily.vagan.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.github.royalfamily.vagan.Application
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityLoginBinding
import com.github.royalfamily.vagan.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
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

        binding.btnGoogle.setOnClickListener {
            loginWithGoogleAccount()
        }
    }


    private fun loginWithKakaoTalk() {
        // 우선 카카오 앱으로 로그인을 시도하고, 실패하면 kakao 계정 로그인을 호출한다.
        UserApiClient.instance
            .loginWithKakaoTalk(this) { oAuthToken: OAuthToken?, error: Throwable? ->
                if (error != null) {
                    loginWithKakaoAccount()
                } else if (oAuthToken != null) {
                    viewModel.updateAccessToken(oAuthToken.accessToken)
                }
            }
    }

    private fun loginWithKakaoAccount() {
        UserApiClient.instance
            .loginWithKakaoAccount(this) { oAuthToken: OAuthToken?, error: Throwable? ->
                if (error != null) {
                    showToast("로그인 실패 $error")
                } else if (oAuthToken != null) {
                    viewModel.updateAccessToken(oAuthToken.accessToken)
                }
            }
    }

    private fun loginWithNaverAccount() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                showToast("로그인 실패 $errorCode : $errorDescription")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onSuccess() {
                NaverIdLoginSDK.getAccessToken()?.let { viewModel.updateAccessToken(it) }
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    private fun loginWithGoogleAccount() {
//        null이면 로그인 안한 상태
        val account = GoogleSignIn.getLastSignedInAccount(this@LoginActivity)
//        if (account != null) {
//            Log.d("test", "=============== ${account}")
//            showToast("이미 로그인 함 : "+account.idToken.toString())
//        } else {
            val signInIntent = Application.mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {

                showToast(acct.serverAuthCode ?: String())
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            showToast(e.statusCode.toString())
        }
    }

}