package com.prim.icons;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/2/26 - 7:36 AM
 */
public class ExMoudle implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";//assest 下的ttf 名字
    }

    @Override
    public Icon[] characters() {
        return ExIcons.values();
    }
}
