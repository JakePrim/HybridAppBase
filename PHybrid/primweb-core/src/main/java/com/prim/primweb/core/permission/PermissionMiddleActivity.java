package com.prim.primweb.core.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/16 0016
 * 描    述：权限跳转的中间件Activity 处理权限
 * 此Activity为透明的activity
 * 修订历史：
 * ================================================
 */
public class PermissionMiddleActivity extends Activity {

    private static final String KEY = "Permission";
    private static final String KEY_VALUE = "Permission";

    public static void startCheckPermission(Activity activity, String permissionType) {
        Intent intent = new Intent(activity, PermissionMiddleActivity.class);
        intent.putExtra(KEY, permissionType);
        activity.startActivity(intent);
    }

    public static void startCheckPermission(Activity activity, String permissionType, PermissionListener permissionListener) {
        mPermissionListener = new WeakReference<>(permissionListener);
        Intent intent = new Intent(activity, PermissionMiddleActivity.class);
        intent.putExtra(KEY, permissionType);
        activity.startActivity(intent);
    }

    /** 权限请求失败的监听 */
    public interface PermissionListener {
        void requestPermissionSuccess(String permissionType);

        void requestPermissionFailed(String permissionType);
    }

    private static WeakReference<PermissionListener> mPermissionListener;

    public static void setPermissionListener(PermissionListener permissionListener) {
        mPermissionListener = new WeakReference<>(permissionListener);
    }

    private String permissionType;


    private void clearListener() {
        mPermissionListener = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        permissionType = intent.getStringExtra(KEY);
        if (TextUtils.isEmpty(permissionType)) {
            clearListener();
            this.finish();
            return;
        }
        requestPermission(permissionType);
    }

    private void requestPermission(String permissionType) {
        checkPermission(permissionType);
    }

    /** 检查相机的权限 */
    private void checkPermission(String permissionType) {
        if (permissionType.equals(WebPermission.CAMERA_TYPE)) {// 注意这里必须要文件的权限
            checkRealPermission(permissionType, 26, WebPermission.PERMISSION_CAMERA_MARK, WebPermission.CAMERA);
        } else if (permissionType.equals(WebPermission.LOCATION_TYPE)) {
            checkRealPermission(permissionType, 0, WebPermission.PERMISSIONS_LOCATION_MARK, WebPermission.LOCATION);
        } else if (permissionType.equals(WebPermission.RECORD_AUDIO_TYPE)) {
            checkRealPermission(permissionType, 27, WebPermission.PERMISSION_RECORD_AUDIO_MARK, WebPermission.RECORD_AUDIO);
        }
    }

    private void checkRealPermission(String permissionType, int op, int permissionRecordAudioMark, String[] recordAudio) {
        if (Build.VERSION.SDK_INT < 23) {
            checkOp(permissionType, op);
        } else {
            MPermissions.requestPermissions(this, permissionRecordAudioMark, recordAudio);
        }
    }

    private void checkOp(String permissionType, int op) {
        if (PermissionCheckUtil.checkOp(this, op)) {
            if (null != mPermissionListener && mPermissionListener.get() != null) {
                mPermissionListener.get().requestPermissionSuccess(permissionType);
            }
            mPermissionListener = null;
            finish();
        } else {
            if (null != mPermissionListener && mPermissionListener.get() != null) {
                mPermissionListener.get().requestPermissionFailed(permissionType);
            }
            mPermissionListener = null;
            finish();
        }
    }

    @PermissionGrant(WebPermission.PERMISSIONS_LOCATION_MARK)
    public void requestLocationSuccess() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionSuccess(permissionType);
        }
        mPermissionListener = null;
        finish();
    }

    @PermissionDenied(WebPermission.PERMISSIONS_LOCATION_MARK)
    public void requestLocationFailed() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionFailed(permissionType);
        }
        mPermissionListener = null;
        finish();
    }

    @PermissionGrant(WebPermission.PERMISSION_CAMERA_MARK)
    public void requestCameraSuccess() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionSuccess(permissionType);
        }
        mPermissionListener = null;
        finish();
    }


    @PermissionDenied(WebPermission.PERMISSION_CAMERA_MARK)
    public void requestCameraFailed() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionFailed(permissionType);
        }
        mPermissionListener = null;
        finish();
    }

    @PermissionGrant(WebPermission.PERMISSION_RECORD_AUDIO_MARK)
    public void requestRecordSuccess() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionSuccess(permissionType);
        }
        mPermissionListener = null;
        finish();
    }

    @PermissionDenied(WebPermission.PERMISSION_RECORD_AUDIO_MARK)
    public void requestRecordFailed() {
        if (null != mPermissionListener && mPermissionListener.get() != null) {
            mPermissionListener.get().requestPermissionFailed(permissionType);
        }
        mPermissionListener = null;
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
