package com.prim.fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import cn.prim.http.lib_net.PrimHttp;
import cn.prim.http.lib_net.callback.Callback;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络框架-primhttp
 * @time 2019/1/4 - 12:09 PM
 */
public class HttpActivity extends AppCompatActivity {
    private static final String TAG = "HttpActivity";
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //af98a8eee3d82a6149159b123ff46bc7
        mSsoHandler = new SsoHandler(this);
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                AccessTokenKeeper.writeAccessToken(HttpActivity.this, oauth2AccessToken);
                String appKey = "2301361998";
                String appSecret = "97f393ca7abd6309a4ff336ea32ada60";
                PrimHttp.getInstance()
                        .get("2/statuses/home_timeline.json")
                        .tag("test")
                        .params("access_token", oauth2AccessToken.getToken())
                        .enqueue(new Callback<Object>() {
                            @Override
                            public void onStart() {
                                Log.e(TAG, "onStart: 开始请求");
                            }

                            @Override
                            public void onSuccess(Object o) {
                                Log.e(TAG, "onSuccess: " + o);
                            }

                            @Override
                            public void onCacheSuccess() {

                            }

                            @Override
                            public void onError() {
                                Log.e(TAG, "onError: ");
                            }

                            @Override
                            public void onFinish() {
                                Log.e(TAG, "onFinish: ");
                            }

                            @Override
                            public void uploadProgress() {

                            }

                            @Override
                            public void downloadProgress() {

                            }
                        });
            }

            @Override
            public void cancel() {

            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
