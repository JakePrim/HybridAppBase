package com.prim_skin.skin;

import android.util.AttributeSet;
import android.view.View;

import com.prim_skin.bean.SkinBean;
import com.prim_skin.bean.SkinView;
import com.prim_skin.utils.SkinTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 处理属性
 * @time 2018/12/12 - 11:56 PM
 */
public class SkinAttribute {

    private static final List<String> mAttributes = new ArrayList<>();

    private List<SkinView> skinViewList = new ArrayList<>();

    //一系列的属性集合
    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
    }

    public void load(View view, AttributeSet attrs) {
        List<SkinBean> skinBeanList = new ArrayList<>();
        //筛选属性
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获得属性名 color src 等
            String attributeName = attrs.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {//如果包含这些属性
                String attributeValue = attrs.getAttributeValue(i);
                //@color/....  也可能是 #000000如果写死了就不能换了
                if (attributeValue.startsWith("#")) {
                    continue;
                }
                int resId;
                if (attributeValue.startsWith("?")) {// ?/  attr的资源
                    //attr id
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    //根据attr id 获得资源ID
                    resId = SkinTools.getResId(view.getContext(), attrId)[0];
                } else {
                    //@123123
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
                if (resId != 0) {
                    //可以被替换的属性，存放到list中
                    SkinBean skinBean = new SkinBean(attributeName, resId);
                    skinBeanList.add(skinBean);
                }
            }
        }
        //
        if (!skinBeanList.isEmpty()) {
            SkinView skinView = new SkinView(view, skinBeanList);
            skinView.applySkin();
            skinViewList.add(skinView);
        }
    }

    public void applySkin() {
        for (SkinView skinView : skinViewList) {
            skinView.applySkin();
        }
    }
}
