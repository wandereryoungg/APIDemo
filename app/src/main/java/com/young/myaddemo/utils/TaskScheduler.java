package com.young.myaddemo.utils;

import android.content.Context;

import com.rbt.vrde.core.CoreManager;
import com.young.myaddemo.Model.EventsPolicy;

public class TaskScheduler {

    private Context mContext;
    private EventsPolicy mEventsPolicy;
    private static TaskScheduler sInstance;

    private TaskScheduler(Context context) {
        this.mContext = context;
        mEventsPolicy = new EventsPolicy(mContext);
    }

    public static TaskScheduler getInstance() {
        if (sInstance == null) {
            sInstance = new TaskScheduler(CoreManager.getContext());
        }
        return sInstance;
    }

    public EventsPolicy getEventsPolicy() {
        if (mEventsPolicy == null) {
            mEventsPolicy = new EventsPolicy(mContext);
        }
        return mEventsPolicy;
    }

}
