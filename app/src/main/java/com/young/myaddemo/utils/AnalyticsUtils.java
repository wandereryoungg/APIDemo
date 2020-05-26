package com.young.myaddemo.utils;

import android.content.Context;

public class AnalyticsUtils {

    protected static final String PREFS_NAME = "test";
    protected static final String KEY_IMEI = com.rbt.vrde.utils.R.analytics.AnalyticsUtils._ay_imei;

    public static String getImei(Context context) {
        CryptPrefs prefs = CryptPrefs.getPrefs(context, PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_IMEI, null);
    }

}
