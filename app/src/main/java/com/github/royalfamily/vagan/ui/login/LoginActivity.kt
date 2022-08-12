package com.github.royalfamily.vagan.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.royalfamily.vagan.ui.login.base.BaseActivity
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var viewModel: LoginViewModel
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this@LoginActivity)[LoginViewModel::class.java]

    }

    override fun setListener() {
        super.setListener()

        
        // Databinding을 해주었기 때문에, 위에서 선언한 binding 키워드로 xml의 view에 findViewById 없이 접근할 수 있어요.
        // xml에선 snake 표기법을 사용하는데, binding에서 id를 camel 표기법으로 변환하기때문에 btnRequest로 접근합니다.
        binding.btnRequest.setOnClickListener {
            
        }
    }
}