package com.app.tuppit.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by david on 17/5/17.
 */

public class App extends Application
{
    private static App mApp = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApp = this;
    }
    public static Context context()
    {
        return mApp.getApplicationContext();
    }
}
