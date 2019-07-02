package com.prim.lib_base.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper

/**
 * @desc
 * @author prim
 * @time 2019-07-02 - 11:49
 * @version 1.0.0
 */
/**
 * Created by benny on 2018/4/21.
 */
val View.contextThemeWrapper: ContextThemeWrapper
    get() = context.contextThemeWrapper

val Context.contextThemeWrapper: ContextThemeWrapper
    get() = when (this) {
        is ContextThemeWrapper -> this
        is ContextWrapper -> baseContext.contextThemeWrapper
        else -> throw IllegalStateException("Context is not an Activity, can't get theme: $this")
    }

@StyleRes
fun View.attrStyle(@AttrRes attrColor: Int): Int = contextThemeWrapper.attrStyle(attrColor)

@StyleRes
private fun ContextThemeWrapper.attrStyle(@AttrRes attrRes: Int): Int =
    attr(attrRes) {
        it.getResourceId(0, 0)
    }

private fun <R> ContextThemeWrapper.attr(@AttrRes attrRes: Int, block: (TypedArray) -> R): R {
    val typedValue = TypedValue();
    if (!theme.resolveAttribute(attrRes, typedValue, true)) throw IllegalArgumentException("$attrRes is not resolvable")
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(attrRes));
    val result = block(a)
    a.recycle()
    return result
}