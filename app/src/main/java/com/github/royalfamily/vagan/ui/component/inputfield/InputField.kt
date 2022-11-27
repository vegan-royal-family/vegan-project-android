package com.github.royalfamily.vagan.ui.component.inputfield

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.github.royalfamily.vagan.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputField: LinearLayout {

    lateinit var layout: LinearLayout
    lateinit var textLayout: TextInputLayout
    lateinit var text: TextInputEditText

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    private fun initView() {
        val infService: String = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v: View = li.inflate(R.layout.component_inputfield, this, false)
        addView(v)

        layout = findViewById(R.id.btn_layout)
        textLayout = findViewById(R.id.text_layout)
        text = findViewById(R.id.field)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.BasicButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BasicButton, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {

//        setColorSystem(typedArray.getInteger(R.styleable.BasicButton_colorClass, 1))
//        typedArray.getString(R.styleable.BasicButton_text)?.let { setText(it) }
//        setButtonSize(typedArray.getInteger(R.styleable.BasicButton_size, SIZE_MEDIUM))
//        setIcon(typedArray)
//        setStatus(typedArray.getInteger(R.styleable.BasicButton_status, STATUS_ENABLED))

        typedArray.recycle()

    }


}