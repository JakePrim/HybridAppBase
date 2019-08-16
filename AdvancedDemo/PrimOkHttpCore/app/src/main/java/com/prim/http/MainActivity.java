package com.prim.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prim.http.net.Call;
import com.prim.http.net.CallBack;
import com.prim.http.net.HttpClient;
import com.prim.http.net.Request;
import com.prim.http.net.RequestBody;
import com.prim.http.net.Response;

public class MainActivity extends AppCompatActivity {

    private HttpClient httpClient;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpClient = new HttpClient.Builder().retrys(3).build();
    }

    public void get(View view) {
        Request request = new Request.Builder()
                .get()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=222222222")
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new CallBack() {
            @Override
            public void onFailure(Call call, Throwable throwable) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "onResponse: " + response.getBody());
            }
        });

    }

    public void post(View view) {
        RequestBody body = new RequestBody()
                .add("city", "北京")
                .add("key", "13cb58f5884f9749287abbead9c658f2");

        Request request = new Request.Builder()
                .post(body)
                .url("http://restapi.amap.com/v3/weather/weatherInfo")
                .build();

        httpClient.newCall(request).enqueue(new CallBack() {
            @Override
            public void onFailure(Call call, Throwable throwable) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "onResponse: " + response.getBody());
            }
        });
    }
}
