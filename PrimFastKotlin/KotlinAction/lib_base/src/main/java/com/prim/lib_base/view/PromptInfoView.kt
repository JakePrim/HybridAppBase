package com.prim.lib_base.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import com.prim.lib_base.utils.no
import com.prim.lib_base.utils.yes
import org.jetbrains.anko.*

/**
 * @desc error prompt info view
 * @author prim
 * @time 2019-06-18 - 14:46
 * @version 1.0.0
 */
class PromptInfoView(val parentView: ViewGroup) : _RelativeLayout(parentView.context), View.OnClickListener {


    private var textView: TextView

    var isShowing = false

    init {
        backgroundColor = Color.WHITE
        textView = textView {
            textSize = 16f
            textColor = Color.BLACK
            padding = dip(5)
            setOnClickListener(this@PromptInfoView)
        }.lparams {
            centerInParent()
        }
    }

    fun show(text: String) {
        isShowing.no {
            parentView.addView(this, matchParent, matchParent)
            alpha = 0f
            animate().alpha(1f).setDuration(100).start()
            isShowing = true
        }
        textView.text = text
    }

    fun show(@StringRes textRes: Int) {
        show(context.getString(textRes))
    }

    fun dissmiss() {
        isShowing.yes {
            parentView.startViewTransition(this)
            parentView.removeView(this)
            animate().alpha(0f).setDuration(100).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    parentView.endViewTransition(this@PromptInfoView)
                }
            }).start()
            isShowing = false
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            textView -> {

            }
        }
    }

}