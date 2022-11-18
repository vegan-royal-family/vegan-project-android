package com.github.royalfamily.vagan.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.github.royalfamily.vagan.ui.base.BaseActivity
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityMainBinding
import com.github.royalfamily.vagan.ui.component.button.CustomTextButton
import com.github.royalfamily.vagan.ui.login.LoginActivity

class MainActivity : BaseActivity() {
    /*
        layout을 data binding layout으로 선언하면, xml 파일명에 카멜케이스 형태로 네이밍 되어 클래스가 자동생성 됩니다.
        activity_main.xml을 data bidning layout으로 선언 했기에, ViewDataBinding을 상속받은 ActivityMainBinding이 자동 생성된 것입니다.
        BaseActivity에서 선언한 binding은 아래 코드의 by 키워드를 통해 초기화 됩니다.
        BaseActivity에서 상속받아 선언하는 것이 아니라면,
        var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) 로 사용하면 됩니다.
     */
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
//        binding 통해 layout을 연결해주었으니 setContentView는 생략합니다.
        super.onCreate(savedInstanceState)

//        나중에 LiveData 사용시, LiveData의 생명주기가 해당 액티비티에 따른다는 것을 명시하는 것입니다.
        binding.lifecycleOwner = this@MainActivity

    }


    //    BaseActivity의 setListener를 재정의하고, 이 함수는 super인 BaseActivity의 onResume에서 호출됩니다.
    override fun setListener() {
        super.setListener()

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnTxt.setOnClickListener {
            binding.btnTxt.setStatus(binding.btnTxt.STATUS_DISABLED)
//            Log.d("test", "hello==========")
        }
    }

}