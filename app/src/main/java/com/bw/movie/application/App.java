package com.bw.movie.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者：owner on 2018/11/28 11:39
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxb3852e6a6b7d9516 ");

    }

    private static App mainApp;

    public static IWXAPI mWxApi;
    public static App getMainApp(){
        mainApp = new App();
        return mainApp;
    }


}
