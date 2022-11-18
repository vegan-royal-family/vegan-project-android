package com.github.royalfamily.vagan.ui.component.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.util.SizeUtil

//class IconButton : LinearLayout {
//
//    private val buttonUtil = ButtonUtil(context)
//
//    private lateinit var layout: LinearLayout
//    private lateinit var icMain: ImageView
//    private lateinit var icProgress: ImageView
//
//    private val sizeUtil: SizeUtil = SizeUtil(context.resources)
//
//    private var backgroundColorDefault = buttonUtil.primaryBackgroundColorDefault
//    private var backgroundColorHover = buttonUtil.primaryBackgroundColorHover
//    private var backgroundColorLoading = buttonUtil.primaryBackgroundColorLoading
//    private var backgroundColorDisabled = buttonUtil.primaryBackgroundColorDisabled
//
//    private var contentsColorDefault = buttonUtil.primaryContentsColorDefault
//    private var contentsColorHover = buttonUtil.primaryContentsColorHover
//    private var contentsColorLoading = buttonUtil.primaryContentsColorLoading
//    private var contentsColorDisabled = buttonUtil.primaryContentsColorDisabled
//
//    constructor(context: Context?) : super(context) {
//        initView()
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
//        initView()
//        getAttrs(attrs)
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs) {
//        initView()
//        getAttrs(attrs, defStyle)
//    }
//
//    private fun initView() {
//        val infService: String = Context.LAYOUT_INFLATER_SERVICE
//        val li = context.getSystemService(infService) as LayoutInflater
//        val v: View = li.inflate(R.layout.component_button_icon, this, false)
//        addView(v)
//
//        layout = findViewById(R.id.btn_layout)
//        icMain = findViewById(R.id.ic_main)
//        icProgress = findViewById(R.id.ic_progress)
//    }
//
//    private fun getAttrs(attrs: AttributeSet?) {
//        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconButton)
//        setTypeArray(typedArray)
//    }
//
//    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
//        val typedArray =
//            context.obtainStyledAttributes(attrs, R.styleable.IconButton, defStyle, 0)
//        setTypeArray(typedArray)
//    }
//
//    private fun setTypeArray(typedArray: TypedArray) {
//
//        setColorSystem(typedArray.getInteger(R.styleable.IconButton_colorClass, 1))
//        setButtonSize(typedArray.getInteger(R.styleable.IconButton_size, buttonUtil.SIZE_MEDIUM))
//        setIcon(typedArray)
//        setStatus(typedArray.getInteger(R.styleable.IconButton_status, buttonUtil.STATUS_ENABLED))
//
//        typedArray.recycle()
//
//    }
//
//    private fun setColorSystem(typed: Int) {
//        when (typed) {
//            1 -> {
//                backgroundColorDefault = buttonUtil.primaryBackgroundColorDefault
//                backgroundColorHover = buttonUtil.primaryBackgroundColorHover
//                backgroundColorLoading = buttonUtil.primaryBackgroundColorLoading
//                backgroundColorDisabled = buttonUtil.primaryBackgroundColorDisabled
//
//                contentsColorDefault = buttonUtil.primaryContentsColorDefault
//                contentsColorHover = buttonUtil.primaryContentsColorHover
//                contentsColorLoading = buttonUtil.primaryContentsColorLoading
//                contentsColorDisabled = buttonUtil.primaryContentsColorDisabled
//            }
//            2 -> {
//                backgroundColorDefault = buttonUtil.secondaryBackgroundColorDefault
//                backgroundColorHover = buttonUtil.secondaryBackgroundColorHover
//                backgroundColorLoading = buttonUtil.secondaryBackgroundColorLoading
//                backgroundColorDisabled = buttonUtil.secondaryBackgroundColorDisabled
//
//                contentsColorDefault = buttonUtil.secondaryContentsColorDefault
//                contentsColorHover = buttonUtil.secondaryContentsColorHover
//                contentsColorLoading = buttonUtil.secondaryContentsColorLoading
//                contentsColorDisabled = buttonUtil.secondaryContentsColorDisabled
//            }
//        }
//    }
//
//    private fun setIcon(typedArray: TypedArray) {
//        val leftIcon = typedArray.getDrawable(R.styleable.IconButton_setIcon)
//        if (leftIcon != null) {
//            icMain.background = leftIcon
//        }
//    }
//
//    private fun setButtonSize(typed: Int) {
//        when (typed) {
//            buttonUtil.SIZE_SMALL -> {
//                layout.setPadding(
//                    sizeUtil.dpToPx(6),
//                    sizeUtil.dpToPx(6),
//                    sizeUtil.dpToPx(6),
//                    sizeUtil.dpToPx(6)
//                )
//            }
//            buttonUtil.SIZE_MEDIUM -> {
//                layout.setPadding(
//                    sizeUtil.dpToPx(10),
//                    sizeUtil.dpToPx(10),
//                    sizeUtil.dpToPx(10),
//                    sizeUtil.dpToPx(10)
//                )
//            }
//            buttonUtil.SIZE_LARGE -> {
//                layout.setPadding(
//                    sizeUtil.dpToPx(16),
//                    sizeUtil.dpToPx(16),
//                    sizeUtil.dpToPx(16),
//                    sizeUtil.dpToPx(16)
//                )
//            }
//        }
//    }
//
//    private fun setStatus(typed: Int) {
//        when (typed) {
//            buttonUtil.STATUS_ENABLED -> {
//                isShowLoading(false)
//                isEnabled(true)
//                layout.setBackgroundResource(R.drawable.bg_btn_default)
//            }
//            buttonUtil.STATUS_LOADING -> {
//                isShowLoading(true)
//                isEnabled(false)
//                layout.setBackgroundResource(R.drawable.bg_btn_loading)
//            }
//            buttonUtil.STATUS_DISABLED -> {
//                isShowLoading(false)
//                isEnabled(false)
//                layout.setBackgroundResource(R.drawable.bg_btn_disabled)
//            }
//        }
//
//    }
//
//    private fun isShowLoading(visible: Boolean) {
//        when (visible) {
//            true -> {
//                val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_progress)
//
//                icProgress.visibility = View.VISIBLE
//                icProgress.animation = animation
//
//                icMain.visibility = View.GONE
//            }
//            false -> {
//                icProgress.apply {
//                    visibility = View.GONE
//                    animation = null
//                }
//
//                icMain.visibility = View.VISIBLE
//
//            }
//        }
//    }
//
//    private fun isEnabled(enabled: Boolean) {
//        layout.isEnabled = !enabled
//        layout.isClickable = !enabled
//    }
//
//
//}