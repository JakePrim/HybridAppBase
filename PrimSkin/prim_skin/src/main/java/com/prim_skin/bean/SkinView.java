package com.prim_skin.bean;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prim_skin.PrimSkin;
import com.prim_skin.utils.SkinResourceTools;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/13 - 1:34 PM
 */
public class SkinView {
    private View view;

    private List<SkinBean> skinBean;

    public SkinView(View view, List<SkinBean> skinBean) {
        this.view = view;
        this.skinBean = skinBean;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<SkinBean> getSkinBean() {
        return skinBean;
    }

    public void setSkinBean(List<SkinBean> skinBean) {
        this.skinBean = skinBean;
    }

    public void applySkin() {
        if (null == view) return;
        Drawable left = null, top = null, rigth = null, bottom = null;
        //遍历属性
        for (SkinBean bean : skinBean) {
            switch (bean.getAttributeName()) {
                case "background":
                    Object background = SkinResourceTools.getInstance().getBackground(bean.getResId());
                    //设置背景 图片/颜色
                    if (background instanceof Drawable) {
                        ViewCompat.setBackground(view, (Drawable) background);
                    } else if (null != background) {
                        view.setBackgroundColor((Integer) background);
                    }
                    break;
                case "textColor":
                    int color = SkinResourceTools.getInstance().getColor(bean.getResId());
                    ((TextView) view).setTextColor(color);
                    break;
                case "src":
                    Object src = SkinResourceTools.getInstance().getBackground(bean.getResId());
                    if (src instanceof Drawable) {
                        ((ImageView) view).setImageDrawable((Drawable) src);
                    } else {
                        ((ImageView) view).setImageDrawable(new ColorDrawable((Integer) src));
                    }
                    break;
                case "drawableLeft":
                    left = SkinResourceTools.getInstance().getDrawable(bean.getResId());
                    break;
                case "drawableTop":
                    top = SkinResourceTools.getInstance().getDrawable(bean.getResId());
                    break;
                case "drawableRight":
                    rigth = SkinResourceTools.getInstance().getDrawable(bean.getResId());
                    break;
                case "drawableBottom":
                    bottom = SkinResourceTools.getInstance().getDrawable(bean.getResId());
                    break;
                default:
                    break;
            }
            if (left != null || top != null || bottom != null || rigth != null) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, rigth, bottom);
            }
        }
    }
}
