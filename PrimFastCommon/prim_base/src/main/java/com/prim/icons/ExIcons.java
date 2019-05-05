package com.prim.icons;

import com.joanzapata.iconify.Icon;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/2/26 - 7:37 AM
 */
public enum ExIcons implements Icon {
    icon_span('\ue606'),
    icon_ali_pay('\ue606');

    private char character;

    ExIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
