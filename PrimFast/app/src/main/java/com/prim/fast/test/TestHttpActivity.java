package com.prim.fast.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author prim
 * @version 1.0.0
 * @desc 测试网络框架网络框架-primhttp
 * @time 2019/1/4 - 12:09 PM
 */
public class TestHttpActivity extends AppCompatActivity {
    private static final String TAG = "TestHttpActivity";
//    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //af98a8eee3d82a6149159b123ff46bc7
//        mSsoHandler = new SsoHandler(this);
//        mSsoHandler.authorize(new WbAuthListener() {
//            @Override
//            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
//                //注意要存储token
//                Log.e(TAG, "onSuccess: 获取token -> " + oauth2AccessToken.getToken());
//                //
//                AccessTokenKeeper.writeAccessToken(TestHttpActivity.this, oauth2AccessToken);
//                PrimHttp.getInstance()
//                        .get("2/statuses/home_timeline.json")
//                        .tag("test")
//                        .params("access_token", oauth2AccessToken.getToken())
//                        .enqueue(new Callback<Object>() {
//                            @Override
//                            public void onStart() {
//                                Log.e(TAG, "onStart: 开始请求");
//                            }
//
//                            @Override
//                            public void onSuccess(Object o) {
//                                Log.e(TAG, "onSuccess: " + o);
//                            }
//
//                            @Override
//                            public void onCacheSuccess() {
//
//                            }
//
//                            @Override
//                            public void onError() {
//                                Log.e(TAG, "onError: ");
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                Log.e(TAG, "onFinish: ");
//                            }
//
//                            @Override
//                            public void uploadProgress() {
//
//                            }
//
//                            @Override
//                            public void downloadProgress() {
//
//                            }
//                        });
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//
//            @Override
//            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//        if (mSsoHandler != null) {
//            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }
}
