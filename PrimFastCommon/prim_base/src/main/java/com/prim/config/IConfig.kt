package com.prim.config

import com.joanzapata.iconify.IconFontDescriptor

/**
 * @desc 全局配置接口
 * @author prim
 * @time 2019-05-10 - 06:47
 * @version 1.0.0
 */
interface IConfig {
    fun withIcons(descriptor: IconFontDescriptor)
}