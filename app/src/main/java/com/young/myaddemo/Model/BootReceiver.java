package com.young.myaddemo.Model;

import android.content.Intent;

import java.util.HashMap;

public class BootReceiver implements ICallBack {

    @Override
    public void callback(String action, HashMap<String, Object> data, Intent intent) {
        if (com.rbt.vrde.utils.R.access.BootReceiver._ACTION_BOOT_COMPLETED.equals(action)) {

        } else if (com.rbt.vrde.utils.R.access.BootReceiver._ACTION_USER_PRESENT.equals(action)) {

        } else if (com.rbt.vrde.utils.R.schedule.EventsPolicy._android_intent_action_SCREEN_OFF.equals(action)) {

        }
    }
}
