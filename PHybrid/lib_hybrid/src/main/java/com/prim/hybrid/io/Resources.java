package com.prim.hybrid.io;

import android.content.Context;

import com.prim.hybrid.entry.Configuration;
import com.prim.hybrid.config.LoadXmlConfig;

import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 3:02 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Resources {
    /**
     * 将配置文件转换为输入流存储在内存中
     * @param path
     * @return
     */
    public static InputStream getResourceAsSteam(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

    /**
     * 获取布局的资源id
     *
     * @param context
     * @return
     */
    public static int getLayoutResource(Context context, String layoutName) {
        int resId = context.getResources().getIdentifier(layoutName, "layout", context.getPackageName());
        return resId;
    }

    /**
     * 获取color的资源id
     * @param context
     * @param colorName
     * @return
     */
    public static int getColorResource(Context context, String colorName) {
        int resId = context.getResources().getIdentifier(colorName, "color", context.getPackageName());
        return resId;
    }

    /**
     * 获取id的资源id
     * @param context
     * @param idName
     * @return
     */
    public static int getIdResource(Context context, String idName) {
        int resId = context.getResources().getIdentifier(idName, "id", context.getPackageName());
        return resId;
    }

    public static void main(String[] args) throws DocumentException {
        InputStream inputStream = Resources.getResourceAsSteam("demo-hybrid-config.xml");
        LoadXmlConfig defaultLoadTemplate = new LoadXmlConfig();
        Configuration configuration = defaultLoadTemplate.parseXml(inputStream);
        System.out.println(configuration);
    }
}
