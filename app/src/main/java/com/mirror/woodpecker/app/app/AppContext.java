package com.mirror.woodpecker.app.app;

import dev.mirror.library.android.app.BaseAppContext;

/**
 * Created by dongqian on 16/1/3.
 */
public class AppContext extends BaseAppContext {

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static AppContext getInstance(){

        return instance;
    }
}
