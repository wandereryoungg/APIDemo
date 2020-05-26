package com.young.myaddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;

import com.young.myaddemo.Model.EventsPolicy;
import com.young.myaddemo.utils.TaskScheduler;

public class ar extends BroadcastReceiver {

    private EventsPolicy mEventsReceiver;

    public ar() {
        mEventsReceiver = TaskScheduler.getInstance().getEventsPolicy();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        AsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mEventsReceiver != null) {
                    //TODO
                }
            }
        });

    }


}

final class AsyncHandler {
    private static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");
    private static final Handler sHandler;

    static {
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper());
    }

    public static void post(Runnable r) {
        sHandler.post(r);
    }

}
