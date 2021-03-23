package com.prim.primweb.core.file;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.prim.primweb.core.R;
import com.prim.primweb.core.utils.ImageHandlerUtil;
import com.prim.primweb.core.utils.PrimWebUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/17 0017
 * 描    述：上传文件的回调中间件Activity
 * 修订历史：
 * ================================================
 */
public class FileValueCallbackMiddleActivity extends Activity implements View.OnClickListener {

    private static final String KEY_TYPE = "type";

    private static int ACTION_IMAGE_CAPTURE = 1009;

    private static int ACTION_VIDEO_CAPTURE = 1010;

    private static int FILE_CHOOSER_RESULT_CODE = 1011;

    private static final String INVOKING = "invokingThird";
    private static final String JSUPLOAD = "isJsUpload";

    private CommonDialog commentDialog;

    private boolean PHOTO_VIDEO_FLAG;

    public boolean invokingThird;

    private Uri cameraUri;

    private String imagePaths;

    private boolean isJsUpload;

    public static void getFileValueCallback(Activity activity, String type) {
        Intent intent = new Intent(activity, FileValueCallbackMiddleActivity.class);
        intent.putExtra(KEY_TYPE, type);
        activity.startActivity(intent);
    }

    public static void getFileValueCallback(Activity activity, String type, boolean invokingThird) {
        Intent intent = new Intent(activity, FileValueCallbackMiddleActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(INVOKING, invokingThird);
        activity.startActivity(intent);
    }

    public static void getFileValueCallback(Activity activity, String type, boolean invokingThird, ChooserFileListener chooserFileListener) {
        mChooserFileListener = chooserFileListener;
        Intent intent = new Intent(activity, FileValueCallbackMiddleActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(INVOKING, invokingThird);
        activity.startActivity(intent);
    }

    public static void getFileValueCallback(boolean isJsUpload, Activity activity, String type, boolean invokingThird, ChooserFileListener chooserFileListener) {
        mChooserFileListener = chooserFileListener;
        Intent intent = new Intent(activity, FileValueCallbackMiddleActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(INVOKING, invokingThird);
        intent.putExtra(JSUPLOAD, isJsUpload);
        activity.startActivity(intent);
    }


    public interface ChooserFileListener {
        void updateFile(Uri uri);

        void updateFile(Intent data, int requestCode, int resultCode);

        void updateCancle();
    }

    public interface ThriedChooserListener {
        void jsOpenVideos();

        void jsOpenPick();
    }

    public interface JsUploadChooserCallback {
        void chooserFile(Intent data, String path, String encode);
    }

    public static JsUploadChooserCallback mJsUploadChooserCallback;

    public static ChooserFileListener mChooserFileListener;

    public static ThriedChooserListener mThriedChooserListener;

    public static void setJsUploadChooserCallback(JsUploadChooserCallback jsUploadChooserCallback) {
        mJsUploadChooserCallback = jsUploadChooserCallback;
    }

    public static void removeJsUploadChooserCallback() {
        if (mJsUploadChooserCallback != null) {
            mJsUploadChooserCallback = null;
        }
    }

    public static void setChooserFileListener(ChooserFileListener chooserFileListener) {
        mChooserFileListener =chooserFileListener;
    }

    public static void setThriedChooserListener(ThriedChooserListener thriedChooserListener) {
        mThriedChooserListener = thriedChooserListener;
    }

    public static void removeThriedChooserListener() {
        if (mThriedChooserListener != null) {
            mThriedChooserListener = null;
        }
    }

    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        type = intent.getStringExtra(KEY_TYPE);
        invokingThird = intent.getBooleanExtra(INVOKING, false);
        isJsUpload = intent.getBooleanExtra(JSUPLOAD, false);
        getFile(type);
    }

    private void clear() {
        mThriedChooserListener = null;
        mChooserFileListener = null;
//        mJsUploadChooserCallback = null;
    }

    private void getFile(String type) {
        if (type.equals(FileType.WEB_IMAGE)) {
            PHOTO_VIDEO_FLAG = true;
            showDialog(type);
        } else if (type.equals(FileType.WEB_VIDEO)) {
            PHOTO_VIDEO_FLAG = false;
            showDialog(type);
        } else {
            openFile();
        }
    }

    private void showDialog(String web) {
        CommonBuilder builder = new CommonBuilder(this);
        builder.style(R.style.shareStyles)
                .view(R.layout.dialog_web_layout)
                .gravity(Gravity.BOTTOM)
                .setPicture(R.id.btn_take_a_picture, web)
                .setSencoed(R.id.btn_select_photo, web)
                .addViewOnclick(R.id.btn_take_a_picture, this)
                .addViewOnclick(R.id.btn_dialog_cancel, this)
                .addViewOnclick(R.id.btn_select_photo, this);
        commentDialog = builder.build();
        commentDialog.show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_select_photo) {
            if (PHOTO_VIDEO_FLAG) {
                jsOpenPick();
            } else {
                jsOpenVideos();
            }

        } else if (i == R.id.btn_take_a_picture) {
            if (PHOTO_VIDEO_FLAG) {
                openCapture();
            } else {
                openCamera();
            }
        } else if (i == R.id.btn_dialog_cancel) {
            cancelFilePathCallback();
            if (commentDialog != null) {
                commentDialog.dismiss();
            }
            finish();
        }
    }

    private void jsOpenVideos() {
        if (invokingThird) {
            if (mThriedChooserListener != null ) {
                //调用第三方库逻辑需要自己处理
                mThriedChooserListener.jsOpenVideos();
            }
            commentDialog.dismiss();
            finish();
        } else {
            openFile();
        }
    }

    private void jsOpenPick() {
        if (invokingThird) {
            if (mThriedChooserListener != null) {
                //调用第三方库逻辑需要自己处理
                mThriedChooserListener.jsOpenPick();
            }
            commentDialog.dismiss();
            finish();
        } else {
            openFile();
        }
    }

    /**
     * 打开文件
     */
    public void openFile() {
        try {
            commentDialog.dismiss();
            Intent i = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            } else {
                i = new Intent(Intent.ACTION_GET_CONTENT);
            }
            i.addCategory(Intent.CATEGORY_OPENABLE);
            if (TextUtils.isEmpty(type)) {
                i.setType("*/*");
            } else {
                i.setType(type);
            }
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开照相机拍照 */
    public void openCapture() {
        commentDialog.dismiss();
        try {
            Intent take_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imagePaths = this.getExternalFilesDir("temp").getAbsolutePath()
                    + "/" + (System.currentTimeMillis() + ".jpg");
            // 必须确保文件夹路径存在，否则拍照后无法完成回调
            File vFile = new File(imagePaths);
            if (!vFile.exists()) {
                File vDirPath = vFile.getParentFile();
                vDirPath.mkdirs();
            } else {
                if (vFile.exists()) {
                    vFile.delete();
                }
            }
            cameraUri = PrimWebUtils.getUriForFile(this, getPackageName(), vFile);
            take_intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            startActivityForResult(take_intent, ACTION_IMAGE_CAPTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开照相机录像 */
    public void openCamera() {
        commentDialog.dismiss();
        try {
            // 激活系统的照相机进行录像
            Intent intent = new Intent();
            intent.setAction("android.media.action.VIDEO_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");
            // 保存录像到指定的路径
            imagePaths = this.getExternalFilesDir("temp").getAbsolutePath()
                    + "/" + (System.currentTimeMillis() + ".mp4");
            // 必须确保文件夹路径存在，否则拍照后无法完成回调
            File vFile = new File(imagePaths);
            if (!vFile.exists()) {
                File vDirPath = vFile.getParentFile();
                vDirPath.mkdirs();
            } else {
                if (vFile.exists()) {
                    vFile.delete();
                }
            }
            cameraUri = PrimWebUtils.getUriForFile(this, getPackageName(), vFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            startActivityForResult(intent, ACTION_VIDEO_CAPTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String TAG = "FileValueCallbackMiddle";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode + " | resultCode --> " + resultCode);
        if (requestCode == ACTION_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            updateCapture(data, requestCode, resultCode);
        } else if (requestCode == ACTION_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            updateCamera(data, requestCode, resultCode);
        } else if (requestCode == ACTION_IMAGE_CAPTURE && resultCode == RESULT_CANCELED) {
            cancelFilePathCallback();
        } else if (requestCode == ACTION_VIDEO_CAPTURE && resultCode == RESULT_CANCELED) {
            cancelFilePathCallback();
        } else if (requestCode == FILE_CHOOSER_RESULT_CODE && resultCode == RESULT_OK) {
            requestImgVideo(data, requestCode, resultCode);
        } else if (requestCode == FILE_CHOOSER_RESULT_CODE && resultCode == RESULT_CANCELED) {
            cancelFilePathCallback();
        }
    }

    /** 上传视频或者图片 */
    private void requestImgVideo(Intent data, int requestCode, int resultCode) {
        if (isJsUpload) {
            String path = PrimWebUtils.uriToPath(this, processData(data)[0]);
            try {
                new EncodeFileThread(path, data, mJsUploadChooserCallback).start();
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        } else {
            if (mChooserFileListener != null) {
                mChooserFileListener.updateFile(data, requestCode, resultCode);
            }
            mChooserFileListener = null;
            finish();
        }

    }

    private class EncodeFileThread extends Thread {
        private String path;
        private Intent data;
        private JsUploadChooserCallback mJsUploadChooserCallback;

        EncodeFileThread(String path, Intent data, JsUploadChooserCallback mJsUploadChooserCallback) {
            this.path = path;
            this.data = data;
            this.mJsUploadChooserCallback = mJsUploadChooserCallback;
        }

        @Override
        public void run() {
            String encode = encode(path);
            if (mJsUploadChooserCallback != null) {
                Log.e(TAG, "requestImgVideo: path --> " + mJsUploadChooserCallback + " path --> " + path + " | encode --> " + encode);
                mJsUploadChooserCallback.chooserFile(data, path, encode);
            }
            finish();
        }
    }

    private Uri[] processData(Intent data) {
        Uri[] datas = null;
        if (data == null) {
            return datas;
        }
        String target = data.getDataString();
        if (!TextUtils.isEmpty(target)) {
            return datas = new Uri[]{Uri.parse(target)};
        }
        ClipData mClipData = data.getClipData();
        if (mClipData != null && mClipData.getItemCount() > 0) {
            datas = new Uri[mClipData.getItemCount()];
            for (int i = 0; i < mClipData.getItemCount(); i++) {

                ClipData.Item mItem = mClipData.getItemAt(i);
                datas[i] = mItem.getUri();

            }
        }
        return datas;
    }

    private static String encode(String path) {
        //decode to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //compress bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] bytes = baos.toByteArray();
        //base64 encode
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /** 上传拍摄的视频 */
    private void updateCamera(Intent data, int requestCode, int resultCode) {
        Uri uri = null;
        uri = cameraUri;
        if (isJsUpload) {
            String path = PrimWebUtils.uriToPath(this, uri);
            if (mJsUploadChooserCallback != null) {
                mJsUploadChooserCallback.chooserFile(data, path, null);
            }
            finish();
        } else {
            if (mChooserFileListener != null) {
                mChooserFileListener.updateFile(uri);
            }
            mChooserFileListener = null;
            finish();
        }

    }

    /** 上传拍的照片 */
    private void updateCapture(Intent data, int requestCode, int resultCode) {
        Uri uri = null;
        ImageHandlerUtil.afterOpenCamera(imagePaths, this);
        uri = cameraUri;
        if (isJsUpload) {
            String encode = null;
            String path = null;
            try {
                path = PrimWebUtils.uriToPath(this, uri);
                encode = encode(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mJsUploadChooserCallback != null) {
                mJsUploadChooserCallback.chooserFile(data, path, encode);
            }
            finish();
        } else {
            if (mChooserFileListener != null) {
                mChooserFileListener.updateFile(uri);
            }
            mChooserFileListener = null;
            finish();
        }

    }

    /**
     * 取消mFilePathCallback回调
     */
    private void cancelFilePathCallback() {
        if (mChooserFileListener != null) {
            mChooserFileListener.updateCancle();
        }
        mChooserFileListener = null;
        finish();
    }

    @Override
    protected void onDestroy() {
        if (commentDialog != null) {
            commentDialog.dismiss();
            commentDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        clear();
        super.finish();
    }
}
