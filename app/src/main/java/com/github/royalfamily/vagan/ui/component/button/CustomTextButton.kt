package com.github.royalfamily.vagan.ui.component.button

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import com.github.royalfamily.vagan.R
import com.github.royalfamily.vagan.util.SizeUtil


class CustomTextButton : LinearLayout{

    private lateinit var layout: LinearLayout
    private lateinit var icLeft: ImageView
    private lateinit var icRight: ImageView
    private lateinit var tvContents: TextView

    private val sizeUtil: SizeUtil = SizeUtil(context.resources)

    private var iconTyped: Int = 0

    val STATUS_ENABLED = 1
    val STATUS_DISABLED = 0

    private val SIZE_SMALL = -1
    private val SIZE_MEDIUM = 0
    private val SIZE_LARGE = 1

    private var IC_LEFT = false
    private var IC_RIGHT = false

    private val primaryContentsColorDefault = context.getColor(R.color.primary_500)
    private val primaryContentsColorHover = context.getColor(R.color.primary_600)
    private val primaryContentsColorDisabled = context.getColor(R.color.primary_100)

    private val secondaryContentsColorDefault = context.getColor(R.color.gray_900)
    private val secondaryContentsColorHover = context.getColor(R.color.gray_900)
    private val secondaryContentsColorDisabled = context.getColor(R.color.gray_400)

    private var contentsColorDefault = primaryContentsColorDefault
    private var contentsColorHover = primaryContentsColorHover
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
        val v: View = li.inflate(R.layout.component_text_button, this, false)
        addView(v)

        layout = findViewById(R.id.btn_layout)
        icLeft = findViewById(R.id.ic_left)
        icRight = findViewById(R.id.ic_right)
        tvContents = findViewById(R.id.tv_contents)


    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextButton, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {

        setTextSize(typedArray.getInteger(R.styleable.CustomTextButton_size, SIZE_MEDIUM))
        setIcon(typedArray)

        setColorSystem(typedArray.getInteger(R.styleable.CustomTextButton_colorClass, 1))
        typedArray.getString(R.styleable.CustomTextButton_text)?.let { setText(it) }

        setIconSize(typedArray.getInteger(R.styleable.CustomTextButton_size, SIZE_MEDIUM))

        setStatus(typedArray.getInteger(R.styleable.CustomTextButton_status, STATUS_ENABLED))

        typedArray.recycle()

    }

    private fun setColorSystem(typed: Int) {
        when (typed) {
            1 -> {
                contentsColorDefault = primaryContentsColorDefault
                contentsColorHover = primaryContentsColorHover
                contentsColorDisabled = primaryContentsColorDisabled

                changeColorState(R.color.btn_text_color_primary)?.let {
                    tvContents.setTextColor(it)
                }
            }
            2 -> {
                contentsColorDefault = secondaryContentsColorDefault
                contentsColorHover = secondaryContentsColorHover
                contentsColorDisabled = secondaryContentsColorDisabled

                changeColorState(R.color.btn_text_color_secondary)?.let {
                    tvContents.setTextColor(it)
                }
            }
        }
        if (IC_RIGHT) {
            DrawableCompat.setTint(icRight.drawable, contentsColorDefault)
        }
        if (IC_LEFT) {
            DrawableCompat.setTint(icLeft.drawable, contentsColorDefault)
        }
    }

    private fun setText(typed: String) {
        tvContents.text = typed
    }

    private fun setIcon(typedArray: TypedArray) {
        val leftIcon = typedArray.getDrawable(R.styleable.CustomTextButton_setLeftIcon)
        if (leftIcon != null) {
            setLeftIcon(leftIcon)
        }

        val rightIcon = typedArray.getDrawable(R.styleable.CustomTextButton_setRightIcon)
        if (rightIcon != null) {
            setRightIcon(rightIcon)
        }
    }

    private fun setIconSize(typed: Int) {
        when (typed) {
            SIZE_SMALL -> {
                icLeft.layoutParams.width = sizeUtil.dpToPx(16)
                icLeft.layoutParams.height = sizeUtil.dpToPx(16)
            }
            SIZE_MEDIUM -> {
                icLeft.layoutParams.width = sizeUtil.dpToPx(20)
                icLeft.layoutParams.height = sizeUtil.dpToPx(20)
            }
            SIZE_LARGE -> {
                icLeft.layoutParams.width = sizeUtil.dpToPx(24)
                icLeft.layoutParams.height = sizeUtil.dpToPx(24)
            }
        }
    }

    private fun setTextSize(typed: Int) {
        when (typed) {
            SIZE_SMALL -> {
                tvContents.setTextAppearance(R.style.Body4_B)
            }
            SIZE_MEDIUM -> {
                tvContents.setTextAppearance(R.style.Body3_B)
            }
            SIZE_LARGE -> {
                tvContents.setTextAppearance(R.style.Body2_B)
            }
        }
    }

    private fun setLeftIcon(resource: Drawable) {
        icRight.visibility = View.GONE
        icLeft.visibility = View.VISIBLE
        icLeft.setImageDrawable(resource)
        IC_LEFT = true
    }

    private fun setRightIcon(resource: Drawable) {
        icRight.visibility = View.VISIBLE
        icLeft.visibility = View.GONE
        icRight.setImageDrawable(resource)
        IC_RIGHT = true
    }

    fun setStatus(typed: Int) {
        when (typed) {
            STATUS_ENABLED -> {
                isEnabled(true)
//                setColor(primaryContentsColorDefault)
            }
            STATUS_DISABLED -> {
                isEnabled(false)
//                setColor(primaryContentsColorDefault)
            }
        }

    }

    private fun isEnabled(enabled: Boolean) {
        layout.isEnabled = !enabled
        layout.isClickable = !enabled

        when (enabled) {
            true -> {
                contentsColorDefault.apply {
                    tvContents.setTextColor(this)
                    if (IC_LEFT) {
                        DrawableCompat.setTint(icLeft.drawable, this)
                    }
                    if (IC_RIGHT) {
                        DrawableCompat.setTint(icRight.drawable, this)
                    }
                }
                tvContents.setTextColor(contentsColorDefault)
            }
            false -> {
                contentsColorDisabled.apply {
                    tvContents.setTextColor(this)
                    if (IC_LEFT) {
                        DrawableCompat.setTint(icLeft.drawable, this)
                    }
                    if (IC_RIGHT) {
                        DrawableCompat.setTint(icRight.drawable, this)
                    }
                }
            }
        }
    }

    private fun setColor(color: Int) {
        if (IC_LEFT) {
            icLeft.setBackgroundColor(color)
        }
        if (IC_RIGHT) {
            icRight.setBackgroundColor(color)
        }
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

    private fun changeColorState(resId: Int): ColorStateList? {
        var cl: ColorStateList? = null
        try {
            val xpp = resources.getXml(resId)
            cl = ColorStateList.createFromXml(resources, xpp)

            return cl
        } catch (e: Exception) {
        }

        return null
    }

}