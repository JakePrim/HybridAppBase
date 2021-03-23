package com.prim.primweb.core.permission;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/17 0017
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PermissionCheckUtil {
    /**
     * 定位：0or1
     * 悬浮窗：24
     * 相机：26
     * 通知：11 true
     * 录音/录像： 27
     * 录音:OP_RECORD_AUDIO = 27
     * CAMERA = 26
     * other:{@link AppOpsManager}
     *
     * @param context
     * @param op
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class managerClass = manager.getClass();
                Method method = managerClass.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int isAllowNum = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
                if (isAllowNum == AppOpsManager.MODE_IGNORED) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isMIUI()) {
                if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        return device.equals("Xiaomi");
    }
}
