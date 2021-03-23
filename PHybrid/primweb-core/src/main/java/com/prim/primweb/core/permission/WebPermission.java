package com.prim.primweb.core.permission;

import android.Manifest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/17 0017
 * 描    述：webview 常用到的权限,需要请求的权限
 * 修订历史：
 * ================================================
 */
public class WebPermission {

    /**
     * 权限的类型
     */
    public static String CAMERA_TYPE = "CAMERA";
    public static String LOCATION_TYPE = "LOCATION";
    public static String RECORD_AUDIO_TYPE = "RECORD_AUDIO";

    public static final String[] CAMERA;

    public static final String[] LOCATION;

    public static final String[] RECORD_AUDIO;

    /**
     * photos permission mark
     */
    public static final int PERMISSION_CAMERA_MARK = 3001;

    /**
     * location permission mark
     */
    public static final int PERMISSIONS_LOCATION_MARK = 3002;

    /**
     * record permission mark
     */
    public static final int PERMISSION_RECORD_AUDIO_MARK = 3003;

    static {
        CAMERA = new String[]{Manifest.permission.CAMERA};

        LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        RECORD_AUDIO = new String[]{Manifest.permission.RECORD_AUDIO};
    }
}
