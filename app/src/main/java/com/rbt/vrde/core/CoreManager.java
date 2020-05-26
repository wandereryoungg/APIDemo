package com.rbt.vrde.core;

import android.content.Context;

public class CoreManager {
    private static Context mContext;
    private static CoreManager sInstance;

    public CoreManager(Context context) {
        mContext = context;
    }

    public static void initBrush(Context context) {
        if (sInstance == null) {
            sInstance = new CoreManager(context);
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
