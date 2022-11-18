package com.github.royalfamily.vagan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.databinding.ActivityComponentBinding
import com.github.royalfamily.vagan.ui.base.BaseActivity

class ComponentActivity : BaseActivity() {

    private val binding by binding<ActivityComponentBinding>(R.layout.activity_component)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this@ComponentActivity
    }


}