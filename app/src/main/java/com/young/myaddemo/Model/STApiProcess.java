package com.young.myaddemo.Model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.young.myaddemo.utils.RandomUtil;
import com.young.myaddemo.utils.StringUtil;
import com.young.myaddemo.utils.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class STApiProcess extends Thread implements BrushRequestManager.RequestCallback<String> {

    private STApiModel mApiModel;
    private Handler mHandler;

    private int adExposureY;
    private int dx;
    private int dy;
    private int mImgCount;
    private boolean isCallback = false;
    private boolean isClick = false;
    private float adReadyTime;


    public STApiProcess(STApiModel model) {
        this.mApiModel = model;
        mHandler = new MyHandler(Looper.getMainLooper());
    }

    @Override
    public void onSuccessCallBack(String response, Object... objs) {

    }

    @Override
    public void onFailCallback(Exception e) {

    }

    class MyHandler extends Handler {
        //2
        static final int MSG_AD_EXPOSURE = 1000;
        //3
        static final int MSG_AD_CLICK = 1001;
        //1
        static final int MSG_AD_LOAD_IMAGE = 1002;
        //5
        static final int MSG_AD_CLOSE = 1003;
        //4
        static final int MSG_AD_START_DOWNLOAD = 1004;

        public MyHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_AD_EXPOSURE:

                    break;
            }
        }
    }

    @Override
    public void run() {
        executeExposureProcess();
        int loop = 0;
        while (!isCallback && loop < 10) {
            try {
                int r = RandomUtil.getRandomNum(1, 5);
                Thread.sleep(r * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loop++;
        }
        adReadyTime = System.currentTimeMillis();
        loop = 0;
        while (loop < 2) {
            try {
                int r = RandomUtil.getRandomNum(1, 5);
                Thread.sleep(r * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loop++;
        }
        executeClickProcess();

    }

    private void executeClickProcess() {
        if (!isClick) {
            mHandler.obtainMessage(MyHandler.MSG_AD_CLOSE).sendToTarget();
            return;
        }
        boolean clicked = postClickAdv();

    }

    private void executeExposureProcess() {
        if (mApiModel == null) {
            return;
        }
        adExposureY = RandomUtil.getRandomNum(150, SystemUtil.getScreenSize().get("height") - 150);
        dx = RandomUtil.getRandomNum(80, mApiModel.adWidth);
        dy = RandomUtil.getRandomNum(80, mApiModel.adHeight);
        /** 加载广告图片 **/
        if (mApiModel.iconUrls != null && mApiModel.iconUrls.length > 0) {
            for (String url : mApiModel.iconUrls) {
                mImgCount++;
                mHandler.obtainMessage(MyHandler.MSG_AD_LOAD_IMAGE, url).sendToTarget();
            }
        }
        if (mApiModel.imageUrls != null && mApiModel.imageUrls.length > 0) {
            for (String url : mApiModel.imageUrls) {
                mImgCount++;
                mHandler.obtainMessage(MyHandler.MSG_AD_LOAD_IMAGE, url).sendToTarget();
            }
        }
        /** 上报展示 */
        if (mImgCount == 0) {
            mHandler.obtainMessage(MyHandler.MSG_AD_EXPOSURE).sendToTarget();
        }

    }

    private boolean postClickAdv() {
        if (mApiModel.clickUrls == null || mApiModel.clickUrls.length == 0) {
            return false;
        }
        int start = RandomUtil.getRandomNum(1, 4);
        long ct = System.currentTimeMillis();
        mApiModel.event_st = ct - (start * 10);
        mApiModel.event_et = ct;

        if (mApiModel.interactionType == 2) {
            //广点通需要转化
            if (mApiModel.clickUrls != null && mApiModel.clickUrls.length > 0) {
                for (int i = 0; i < mApiModel.clickUrls.length; i++) {
                    mApiModel.clickUrls[i] = replaceUrl(mApiModel.clickUrls[i], dx, dy);
                }
            }

        }
        BrushRequestManager.postExposure(mApiModel.clickUrls[0], null, );

    }

    private String replaceUrl(String oriUrl, int dx, int dy) {
        oriUrl.replace("__DOWN_X__", dx + "")
                .replace("_DOWN_Y_", dy + "")
                .replace("_UP_X_", dx + "")
                .replace("_UP_Y_", dy + "");
        oriUrl.replace("__WIDTH__", mApiModel.adWidth + "")
                .replace("__HEIGHT__", mApiModel.adHeight + "");
        return oriUrl;
    }

    class ExposureCallback implements BrushRequestManager.RequestCallback<String> {

        @Override
        public void onSuccessCallBack(String response, Object... objs) {
            if (StringUtil.isBlank(response)) {
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject dataJson = jsonObject.optJSONObject("data");
                if (jsonObject.optInt("ret") == 0 && dataJson != null) {
                    String clickId = dataJson.optString("clickid");
                    String dstlink = dataJson.optString("dstlink");
                    mApiModel.clickid = clickId;
                    mApiModel.clickUrl = dstlink;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailCallback(Exception e) {

        }
    }
}
