package com.zhufk.customokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhufk.my_okhttp.Call2;
import com.zhufk.my_okhttp.Callback2;
import com.zhufk.my_okhttp.OkHttpClient2;
import com.zhufk.my_okhttp.Request2;
import com.zhufk.my_okhttp.Response2;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void useOkHttp(View view) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(PATH).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttp", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("OkHttp", "请求成功" + response.body().string());
            }
        });
    }

    public void useCustomOkHttp(View view) {
        OkHttpClient2 client2 = new OkHttpClient2.Builder().build();
        Request2 request2 = new Request2.Builder().url(PATH).build();
        Call2 call2 = client2.newCall(request2);
        call2.enqueue(new Callback2() {
            @Override
            public void onFailure(Call2 call, IOException e) {
                Log.e("Custom-OkHttp", "请求失败");
            }

            @Override
            public void onResponse(Call2 call, Response2 response) throws IOException {
                Log.e("Custom-OkHttp", "请求成功" + response.string());
            }
        });
    }
}
