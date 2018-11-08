package com.fgj.commonitemdecoration.demo;

import android.app.Application;

import com.fgj.commonitemdecoration.decoration.DecorationUtil;

/**
 * Created by FGJ on 2018/7/25.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DecorationUtil.init(this);
    }
}
