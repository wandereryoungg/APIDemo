package com.rbt.vrde.core.web.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rbt.vrde.utils.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Roy on 2017/12/19.
 */

public class WebLayout extends FrameLayout {

    private static final String TAG = "WebLayout";

    private WebSettings mSettings;
    private Context mContext;
    private MyWebView mWebView;
    private ProgressBar mProgressbar;
    private IWebViewCallback mCallback;
    private String mTitle;
    private String pageFinishUrl;

    private boolean mTransparent = false;


    private class MyWebView extends WebView {
        public MyWebView(Context context) {
            this(context, null);
        }

        public MyWebView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            setWebViewClient(new WebViewClient());
            setWebChromeClient(new WebChromeClient());
            setLayerType(View.LAYER_TYPE_HARDWARE, null);

            mSettings = getSettings();
            mSettings.setJavaScriptEnabled(true);
            mSettings.setGeolocationEnabled(false);
            mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mSettings.setSupportZoom(false);
            mSettings.setBuiltInZoomControls(false);
            mSettings.setUseWideViewPort(true);
            mSettings.setLoadWithOverviewMode(true);

            mSettings.setDomStorageEnabled(true);
            mSettings.setDatabaseEnabled(false);
            mSettings.setAppCacheEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

        public class WebViewClient extends android.webkit.WebViewClient {

            private boolean isDeepLink(final String url) {
                return !isHttpUrl(url);
            }

            private boolean deviceCanHandleIntent(final Context context, final Intent intent) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    final List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    return !activities.isEmpty();
                } catch (NullPointerException e) {
                    return false;
                }
            }

            private boolean isHttpUrl(final String url) {
                if (url == null) {
                    return false;
                }
                if (url.startsWith(com.rbt.vrde.core.res.R.webpage.AdWebView._http_) || url.startsWith(com.rbt.vrde.core.res.R.webpage.AdWebView._https_)) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                MLog.d(TAG, "---shouldOverrideUrlLoading--url:" + url);

                if (mCallback != null)
                    mCallback.onPageRedirected(url);

                if (url.startsWith(com.rbt.vrde.core.res.R.webpage.AdWebView._tel__)) {
                    return true;
                }

                if (isDeepLink(url)) {
                    /*final Intent intent = new Intent(com.ib.mob.core.res.R.webpage.AdWebView._android_intent_action_VIEW, Uri.parse(url));
                    if (deviceCanHandleIntent(ad.getContext(), intent)) {
                        mContext.startActivity(intent);
                    }*/
                    return true;
                }  else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieStr = cookieManager.getCookie(url);
//                MLog.d(TAG, "---Cookie--url:" + url + "---cookies:" + cookieStr);
                super.onPageFinished(view, url);
                if (mCallback != null && com.rbt.vrde.core.res.R.webpage.AdWebView._unknown_page_.equals(mTitle))
                    mCallback.onPageFinished(url);
            }

            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        }

        public class WebChromeClient extends android.webkit.WebChromeClient {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                MLog.d(TAG, "---load progress:" + newProgress);
                if(mCallback != null)
                    mCallback.onProgress(newProgress);
                if (mTransparent) {
                    /** 页面透明 */
                    String js = com.rbt.vrde.core.res.R.webpage.AdWebView._transparent_js;
                    loadUrl(com.rbt.vrde.core.res.R.webpage.AdWebView._exec_javascript__ + js);

                    mProgressbar.setVisibility(GONE);
                }
                if (newProgress == 100) {
                    if (!mTransparent) {
                        mProgressbar.setVisibility(GONE);
                    }
                    if (mCallback != null && !com.rbt.vrde.core.res.R.webpage.AdWebView._unknown_page_.equals(mTitle)) {
                        String url = view.getUrl();
                        if (StringUtils.isBlank(pageFinishUrl) || !pageFinishUrl.equals(url)) {
                            mCallback.onPageFinished(url);
                        }
                        pageFinishUrl = url;
                    }
                } else {
                    if (!mTransparent) {
                        mProgressbar.setVisibility(VISIBLE);
                    }
                    mProgressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle = title;
                if (mCallback != null) {
                    mCallback.onReceivedTitle(title);
                }
            }
        }
    }

    public WebLayout(Context context) {
        this(context, null);
    }

    public WebLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(com.rbt.vrde.core.res.R.webpage.AdWebView.__00EE00));
        ClipDrawable pd = new ClipDrawable(colorDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        mProgressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 6));
        mProgressbar.setProgressDrawable(pd);


        mContext = context;

        mWebView = new MyWebView(context);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(mWebView, lp);

        addView(mProgressbar);

        mWebView.setLongClickable(true);
        mWebView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int top = mProgressbar.getHeight();
        //mWebView.layout(l, top, r, b);
    }

    public void loadUrl(String url) {
        try {
            if (mWebView != null)
                mWebView.loadUrl(url);
        } catch (Exception e) {}
    }

    public void loadUrl(String url, Map<String, String> headers) {
        try {
            if (mWebView != null)
                mWebView.loadUrl(url, headers);
        } catch (Exception e) {}
    }

    public void setUserAgent(String ua) {
        if (StringUtils.isBlank(ua))
            return;
        mSettings.setUserAgentString(ua);
    }

    public void setTransparent(boolean transparent) {
        mTransparent = transparent;
    }

    public void setCallback(IWebViewCallback callback) {
        mCallback = callback;
    }

    public void onDestroy() {
        /*CookieSyncManager.createInstance(mContext);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.get(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        cookieManager.removeSessionCookie();
        cookieManager.removeExpiredCookie();
        CookieSyncManager.get().sync(); // forces sync manager to sync now*/

        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        //mWebView.clearCache(true);

        /*File appWebCacheDir = new File(mContext.getFilesDir().getParent(), com.ib.mob.core.res.R.webpage.AdWebView._app_webview);
        if (appWebCacheDir.exists()) {
            deleteFile(appWebCacheDir);
        }

        File appCacheDir = mContext.getCacheDir();
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }*/
        try {
            mWebView.stopLoading();
            removeAllViews();
            mWebView.destroy();
        } catch (Throwable e){}
    }

    public void goBack() {
        try {
            if(mWebView != null)
                mWebView.goBack();
        } catch (Exception e) { }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        mWebView.setBackgroundColor(color);
    }

    public void addJavascriptInterfacea(Object obj, String name) {
        mWebView.addJavascriptInterface(obj, name);
    }

    public WebView getWebView() {
        return mWebView;
    }

    /**
     * 递归删除 文件/文件夹
     */
    private void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            /*if(!file.getPath().endsWith(com.ib.mob.core.res.R.webpage.AdWebView._app_webview))
                file.delete();*/
            if (!file.isDirectory())
                file.delete();
        }
    }


    public interface IWebViewCallback {
        void onReceivedTitle(String title);

        void onPageFinished(String url);

        void onPageRedirected(String url);

        void onProgress(int num);
    }

}
