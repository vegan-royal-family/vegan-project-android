package com.github.royalfamily.vagan.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.github.royalfamily.vagan.Application
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.data.Resource
import com.github.royalfamily.vagan.databinding.ActivityLoginBinding
import com.github.royalfamily.vagan.enum.LoginType
import com.github.royalfamily.vagan.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    val googleLoginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                val data = task.getResult(ApiException::class.java)
                viewModel.requestToken(
                    LoginType.KAKAO,
                    data.idToken.toString()
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()

        viewModel.googleAccount = GoogleSignIn.getLastSignedInAccount(this@LoginActivity)
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

    override fun setObserver() {

        viewModel.requestToken.observe(this) {
            Log.d(
                "API_RESPONSE",
                "${it.status}, ${it.statusCode}, ${it.data?.isNew}, ${it.message}"
            )
        }

        viewModel.status.observe(this) {
            when(it) {
                Resource.Status.LOADING -> showLoading(this, true)
                else -> showLoading(this, false)
            }
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
                showToast("로그인 실패 $errorCode : $errorDescription")
            }

            override fun onError(errorCode: Int, message: String) {

                onFailure(errorCode, message)

            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)

    }

    private fun loginWithGoogleAccount() {

        if (viewModel.googleAccount?.idToken.isNullOrEmpty()) {
            val intent = Application.mGoogleSignInClient.signInIntent
            googleLoginLauncher.launch(intent)
        } else {
            viewModel.requestToken(
                LoginType.GOOGLE,
                viewModel.googleAccount?.idToken!!
            )
        }

    }

    fun showLoading(activity: Activity, isShow: Boolean) {
        if (isShow) {
            val linear = LinearLayout(activity)
            linear.tag = "MyProgressBar"
            linear.gravity = Gravity.CENTER
            linear.setBackgroundColor(0x33000000)
            val progressBar = ProgressBar(activity)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressBar.layoutParams = layoutParams
            linear.addView(progressBar)
            linear.setOnClickListener{}
            val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
            rootView.addView(linear)
        } else {
            val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
            val linear = rootView.findViewWithTag<LinearLayout>("MyProgressBar")
            if (linear != null) {
                rootView.removeView(linear)
            }
        }
    }

}