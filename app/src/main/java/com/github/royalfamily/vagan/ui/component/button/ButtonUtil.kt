package com.github.royalfamily.vagan.ui.component.button

import android.content.Context
import com.github.royalfamily.vagan.R

class ButtonUtil (context: Context) {
      val STATUS_ENABLED = 1
      val STATUS_DISABLED = 0
      val STATUS_LOADING = -1

      val SIZE_SMALL = -1
      val SIZE_MEDIUM = 0
      val SIZE_LARGE = 1


      val primaryBackgroundColorDefault = context.getColor(R.color.primary_500)
      val primaryBackgroundColorHover = context.getColor(R.color.primary_600)
      val primaryBackgroundColorLoading = context.getColor(R.color.primary_400)
      val primaryBackgroundColorDisabled = context.getColor(R.color.primary_100)

      val primaryContentsColorDefault = context.getColor(R.color.primary_500)
      val primaryContentsColorHover = context.getColor(R.color.primary_600)
      val primaryContentsColorLoading = context.getColor(R.color.primary_400)
      val primaryContentsColorDisabled = context.getColor(R.color.primary_100)

      val secondaryBackgroundColorDefault = context.getColor(R.color.gray_200)
      val secondaryBackgroundColorHover = context.getColor(R.color.primary_300)
      val secondaryBackgroundColorLoading = context.getColor(R.color.primary_200)
      val secondaryBackgroundColorDisabled = context.getColor(R.color.primary_200)

      val secondaryContentsColorDefault = context.getColor(R.color.gray_900)
      val secondaryContentsColorHover = context.getColor(R.color.gray_900)
      val secondaryContentsColorLoading = context.getColor(R.color.gray_600)
      val secondaryContentsColorDisabled = context.getColor(R.color.gray_400)
}