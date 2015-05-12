package com.xwj.material.component;

import android.app.Application;
import android.content.Context;

import com.xwj.material.R;
import com.xwj.material.views.Toaster;

/**
 * Created by user on 2015/5/12.
 */
public class MaterialApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Toaster.init(mContext, R.layout.toast, android.R.id.message);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
