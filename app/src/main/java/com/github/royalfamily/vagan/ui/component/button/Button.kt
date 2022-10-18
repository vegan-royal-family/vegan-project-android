package com.github.royalfamily.vagan.ui.component.button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.util.SizeUtil

class Button : LinearLayout {

    private lateinit var layout: LinearLayout
    private lateinit var icLeft: ImageView
    private lateinit var icRight: ImageView
    private lateinit var tvContents: TextView
    private lateinit var icProgress: ImageView

    private val sizeUtil: SizeUtil = SizeUtil(context.resources)

    private var iconTyped: Int = 0

    private val STATUS_ENABLED = 1
    private val STATUS_DISABLED = 0
    private val STATUS_LOADING = -1

    private val SIZE_SMALL = -1
    private val SIZE_MEDIUM = 0
    private val SIZE_LARGE = 1


    private var IC_LEFT = false
    private var IC_RIGHT = false

    private val primaryBackgroundColorDefault = context.getColor(R.color.primary_500)
    private val primaryBackgroundColorHover = context.getColor(R.color.primary_600)
    private val primaryBackgroundColorLoading = context.getColor(R.color.primary_400)
    private val primaryBackgroundColorDisabled = context.getColor(R.color.primary_100)

    private val primaryContentsColorDefault = context.getColor(R.color.primary_500)
    private val primaryContentsColorHover = context.getColor(R.color.primary_600)
    private val primaryContentsColorLoading = context.getColor(R.color.primary_400)
    private val primaryContentsColorDisabled = context.getColor(R.color.primary_100)

    private val secondaryBackgroundColorDefault = context.getColor(R.color.gray_200)
    private val secondaryBackgroundColorHover = context.getColor(R.color.primary_300)
    private val secondaryBackgroundColorLoading = context.getColor(R.color.primary_200)
    private val secondaryBackgroundColorDisabled = context.getColor(R.color.primary_200)

    private val secondaryContentsColorDefault = context.getColor(R.color.gray_900)
    private val secondaryContentsColorHover = context.getColor(R.color.gray_900)
    private val secondaryContentsColorLoading = context.getColor(R.color.gray_600)
    private val secondaryContentsColorDisabled = context.getColor(R.color.gray_400)

    private var backgroundColorDefault = primaryBackgroundColorDefault
    private var backgroundColorHover = primaryBackgroundColorHover
    private var backgroundColorLoading = primaryBackgroundColorLoading
    private var backgroundColorDisabled = primaryBackgroundColorDisabled

    private var contentsColorDefault = primaryContentsColorDefault
    private var contentsColorHover = primaryContentsColorHover
    private var contentsColorLoading = primaryContentsColorLoading
    private var contentsColorDisabled = primaryContentsColorDisabled


    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    private fun initView() {
        val infService: String = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v: View = li.inflate(R.layout.component_button, this, false)
        addView(v)

        layout = findViewById(R.id.btn_layout)
        icLeft = findViewById(R.id.ic_left)
        icRight = findViewById(R.id.ic_right)
        tvContents = findViewById(R.id.tv_contents)
        icProgress = findViewById(R.id.ic_progress)
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

        setColorSystem(typedArray.getInteger(R.styleable.BasicButton_colorClass, 1))
        typedArray.getString(R.styleable.BasicButton_text)?.let { setText(it) }
        setButtonSize(typedArray.getInteger(R.styleable.BasicButton_size, SIZE_MEDIUM))
        setIcon(typedArray)
        setStatus(typedArray.getInteger(R.styleable.BasicButton_status, STATUS_ENABLED))

        typedArray.recycle()

    }

    private fun setColorSystem(typed: Int) {
        when (typed) {
            1 -> {
                backgroundColorDefault = primaryBackgroundColorDefault
                backgroundColorHover = primaryBackgroundColorHover
                backgroundColorLoading = primaryBackgroundColorLoading
                backgroundColorDisabled = primaryBackgroundColorDisabled

                contentsColorDefault = primaryContentsColorDefault
                contentsColorHover = primaryContentsColorHover
                contentsColorLoading = primaryContentsColorLoading
                contentsColorDisabled = primaryContentsColorDisabled
            }
            2 -> {
                backgroundColorDefault = secondaryBackgroundColorDefault
                backgroundColorHover = secondaryBackgroundColorHover
                backgroundColorLoading = secondaryBackgroundColorLoading
                backgroundColorDisabled = secondaryBackgroundColorDisabled

                contentsColorDefault = secondaryContentsColorDefault
                contentsColorHover = secondaryContentsColorHover
                contentsColorLoading = secondaryContentsColorLoading
                contentsColorDisabled = secondaryContentsColorDisabled
            }
        }
    }

    private fun setText(typed: String) {
        tvContents.text = typed
    }

    private fun setIcon(typedArray: TypedArray) {
        val leftIcon = typedArray.getDrawable(R.styleable.BasicButton_setLeftIcon)
        if (leftIcon != null) {
            setLeftIcon(leftIcon)
        }

        val rightIcon = typedArray.getDrawable(R.styleable.BasicButton_setRightIcon)
        if (rightIcon != null) {
            setRightIcon(rightIcon)
        }
    }

    private fun setButtonSize(typed: Int) {
        when (typed) {
            SIZE_SMALL -> {
                layout.setPadding(
                    sizeUtil.dpToPx(16),
                    sizeUtil.dpToPx(6),
                    sizeUtil.dpToPx(16),
                    sizeUtil.dpToPx(6)
                )
            }
            SIZE_MEDIUM -> {
                layout.setPadding(
                    sizeUtil.dpToPx(24),
                    sizeUtil.dpToPx(10),
                    sizeUtil.dpToPx(24),
                    sizeUtil.dpToPx(10)
                )
            }
            SIZE_LARGE -> {
                layout.setPadding(
                    sizeUtil.dpToPx(24),
                    sizeUtil.dpToPx(16),
                    sizeUtil.dpToPx(24),
                    sizeUtil.dpToPx(16)
                )
            }
        }
    }

    private fun setLeftIcon(resource: Drawable) {
        icRight.visibility = View.GONE
        icLeft.visibility = View.VISIBLE
        icLeft.background = resource
        IC_LEFT = true
    }

    private fun setRightIcon(resource: Drawable) {
        icRight.visibility = View.VISIBLE
        icLeft.visibility = View.GONE
        icRight.background = resource
        IC_RIGHT = true
    }

    private fun setStatus(typed: Int) {
        when (typed) {
            STATUS_ENABLED -> {
                isShowLoading(false)
                isEnabled(true)
                layout.setBackgroundResource(R.drawable.bg_btn_default)
            }
            STATUS_LOADING -> {
                isShowLoading(true)
                isEnabled(false)
                layout.setBackgroundResource(R.drawable.bg_btn_loading)
            }
            STATUS_DISABLED -> {
                isShowLoading(false)
                isEnabled(false)
                layout.setBackgroundResource(R.drawable.bg_btn_disabled)
            }
        }

    }

    private fun isShowLoading(visible: Boolean) {
        when (visible) {
            true -> {
                val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_progress)

                icProgress.visibility = View.VISIBLE
                icProgress.animation = animation

                View.GONE.also {
                    icLeft.visibility = it
                    icRight.visibility = it
                }
            }
            false -> {
                icProgress.apply {
                    visibility = View.GONE
                    animation = null
                }

                if (IC_LEFT) {
                    icLeft.visibility = View.VISIBLE
                }

                if (IC_RIGHT) {
                    icRight.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isEnabled(enabled: Boolean) {
        layout.isEnabled = !enabled
        layout.isClickable = !enabled
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}