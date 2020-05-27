package com.rbt.vrde.core.web.widget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import android.webkit.WebView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roy on 2018/10/24.
 */

public class CookiesHelper {

    private static final String TAG = "CookiesHelper";

    private Context mContext;
    private static CookiesHelper sInstance;

    static class CookiesDB {

        private Context mContext;

        public CookiesDB(Context ctx) {
            mContext = ctx;
        }

        private SQLiteDatabase openDB() {
            SQLiteDatabase db = null;
            try {
                String dbName = mContext.getFilesDir().getParent() + com.rbt.vrde.core.res.R.webpage.Widget.__app_webview_Cookies;
                File dbFile = new File(dbName);
                if (dbFile.exists()) {
                    db = SQLiteDatabase.openOrCreateDatabase(dbName, null);
                }
            } catch (Throwable e) {
            }
            return db;
        }

        public List<Map<String, String>> queryCookiesByDomain(String domain) {
            List<Map<String, String>> cookies = new ArrayList<Map<String, String>>();
            SQLiteDatabase db = openDB();
            if (db != null) {
                try {
                    String sql = com.rbt.vrde.core.res.R.webpage.Widget.__sql_ + domain + "'";
                    Cursor c = db.rawQuery(sql, null);

//                    MLog.d(TAG, "----Cursor length:" + c.getCount());
                    while (c != null && c.getCount() > 0 && c.moveToNext()) {
                        String host_key = c.getString(c.getColumnIndex(com.rbt.vrde.core.res.R.webpage.Widget._host_key));
                        String name = c.getString(c.getColumnIndex(com.rbt.vrde.core.res.R.webpage.Widget._name));
                        String value = c.getString(c.getColumnIndex(com.rbt.vrde.core.res.R.webpage.Widget._value));
                        Map<String, String> cookie = new HashMap<String, String>();
                        cookie.put(host_key, name + "=" + value);
                        cookies.add(cookie);
                    }


                    if (c != null)
                        c.close();
                    db.close();
                } catch (Exception e) {
                }
            }
            return cookies;
        }

        /*public void updateCookiesByDomain(String domain, List<Map<String, String>> cookies) {
            SQLiteDatabase db = openDB();
            if (db != null) {

                ContentValues values = new ContentValues();

                db.replace("cookies", null, values);
            }
        }*/
    }

    private CookiesHelper(Context context) {
        mContext = context;
    }

    public static CookiesHelper get(Context context) {
        if (sInstance == null)
            sInstance = new CookiesHelper(context);
        return sInstance;
    }

    public void removeAllCookies() {

        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();// Removes all cookies.
        cookieManager.removeSessionCookie();
        cookieManager.removeExpiredCookie();
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        WebView view = new WebView(mContext);
        view.clearCache(true);
        WebStorage.getInstance().deleteAllData();
    }


    private static final String FILE_NAME = com.rbt.vrde.core.res.R.webpage.Utils.___mob_ad_wbtsk;
    private static final String COOKIES_KEY = com.rbt.vrde.core.res.R.webpage.Utils._wb_cks;


}
