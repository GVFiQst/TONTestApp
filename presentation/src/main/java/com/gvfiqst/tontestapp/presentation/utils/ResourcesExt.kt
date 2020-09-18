package com.gvfiqst.tontestapp.presentation.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat


fun Float.dp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

fun Int.dp(context: Context) = toFloat().dp(context).toInt()

fun Context.drawable(@DrawableRes drawableResource: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResource)

fun Context.color(@ColorRes color: Int): Int =
    ContextCompat.getColor(this, color)

fun Context.dimen(@DimenRes dimen: Int): Float =
    resources.getDimension(dimen)

fun Context.dimenOffset(@DimenRes dimen: Int): Int =
    resources.getDimensionPixelOffset(dimen)

fun Context.integer(@IntegerRes integer: Int): Int =
    resources.getInteger(integer)

fun Context.htmlText(@StringRes resId: Int) =
    HtmlCompat.fromHtml(getString(resId), HtmlCompat.FROM_HTML_MODE_COMPACT)

fun Context.htmlText(@StringRes resId: Int, vararg formatArgs: Any?) =
    HtmlCompat.fromHtml(getString(resId, *formatArgs), HtmlCompat.FROM_HTML_MODE_COMPACT)