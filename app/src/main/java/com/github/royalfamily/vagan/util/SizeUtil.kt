package com.github.royalfamily.vagan.util

import android.content.res.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class SizeUtil(val resource: Resources) {

    fun dpToPx(dp: Int): Int {
        val scale: Float = resource.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

}