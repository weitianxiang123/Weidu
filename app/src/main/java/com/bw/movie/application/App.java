package com.bw.movie.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者：owner on 2018/11/28 11:39
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
