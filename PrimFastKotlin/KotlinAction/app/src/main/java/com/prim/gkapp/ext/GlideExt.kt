package com.prim.gkapp.ext

import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @desc glide的扩展类
 * @author prim
 * @time 2019-06-02 - 11:49
 * @version 1.0.0
 */
fun AvatarImageView.loadImage(url: String, text: CharSequence, opetions: RequestOptions = RequestOptions()) {
    text.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }

    Glide.with(this.context)
        .load(url)
        .apply(opetions)
        .into(this)
}