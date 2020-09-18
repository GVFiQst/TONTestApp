package com.gvfiqst.tontestapp.presentation.utils

import android.content.Context
import android.util.TypedValue


fun Float.dp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

fun Int.dp(context: Context) = toFloat().dp(context).toInt()
