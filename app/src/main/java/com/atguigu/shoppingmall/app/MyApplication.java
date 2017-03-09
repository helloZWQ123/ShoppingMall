package com.atguigu.shoppingmall.app;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by LiXin on 2017/2/22.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS).readTimeout(10000L,TimeUnit.MILLISECONDS).build();
        OkHttpUtils.initClient(okHttpClient);
        ZXingLibrary.initDisplayOpinion(this);

    }
}
