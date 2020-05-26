package com.young.myaddemo.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.young.myaddemo.ar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class EventsPolicy {

    public static final String TAG = EventsPolicy.class.getSimpleName();
    private static BroadcastReceiver mSysReceiver;
    //TODO
    private static Hashtable<String, Set<Event>> sEvents = new Hashtable<>();
    private boolean isRegister = false;
    private Context mContext;

    static {
        Class cls = ar.class;
        try {
            Constructor constructor = cls.getConstructor();
            mSysReceiver = (BroadcastReceiver) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public EventsPolicy(Context context) {
        this.mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        BootReceiver receiver = new BootReceiver();
        Event e = new Event(com.rbt.vrde.utils.R.schedule.EventsPolicy._android_intent_action_BOOT_COMPLETED, receiver);
        Set<Event> set = new HashSet<Event>();
        set.add(e);

        Event e2 = new Event(com.rbt.vrde.utils.R.schedule.EventsPolicy._android_intent_action_USER_PRESENT, receiver);
        Set<Event> set2 = new HashSet<Event>();
        set2.add(e2);

        sEvents.put(e.action, set);
        sEvents.put(e2.action, set2);
    }

    public void register(Event event) {
        if (event == null) {
            return;
        }
        String action = event.action;
        if (action == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        Set<Event> events = sEvents.get(action);
    }
}
