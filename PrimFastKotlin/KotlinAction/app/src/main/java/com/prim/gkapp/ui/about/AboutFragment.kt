package com.prim.gkapp.ui.about

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prim.gkapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * about fragment
 * UI use anko
 */
class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return AboutFragmentUI().createView(AnkoContext.create(context!!, this))
    }
}

class AboutFragmentUI : AnkoComponent<AboutFragment> {
    override fun createView(ui: AnkoContext<AboutFragment>): View = ui.apply {
        relativeLayout {
            verticalLayout {
                imageView(R.mipmap.ic_launcher).lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }
                textView(
                    "码乎.GitHub by @JakePrim"
                ).lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    padding = dip(10)
                }
                textView(
                    "open source listener"
                ).lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    onClick {
                        alert {
                            customView {
                                scrollView {
                                    textView {
                                        padding = dip(10)
                                    }
                                }
                            }
                        }.show()
                    }
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.CENTER
            }
        }
    }.view
}
