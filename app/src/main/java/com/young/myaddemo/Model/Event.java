package com.young.myaddemo.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Event {
    String action;
    String clazz;
    HashMap<String, Object> data;
    ICallBack cbk;
    String dataScheme;
    String dataType;
    String category;

    public Event(String action, ICallBack callback) {
        this(action, callback, null);
    }

    public Event(String action, ICallBack callback, HashMap<String, Object> data) {
        //super(action, callback, data);
        this.action = action;
        this.cbk = callback;
        this.clazz = callback.getClass().getName();
        this.data = data;
    }


    public Event(String action, ICallBack callback, HashMap<String, Object> data, String dataScheme, String dataType, String category) {
        this(action, callback, data);
        this.dataScheme = dataScheme;
        this.dataType = dataType;
        this.category = category;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Event) {
            Event e = (Event) obj;
            boolean r = false;
            if (action == null){
                r = null == e.action;
            } else {
                r = action.equals(e.action);
            }
            if (clazz == null) {
                r = r && (null == e.clazz);
            } else {
                r = r && (clazz.equals(e.clazz));
            }
            return r;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._act, action);
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._cls, clazz);
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._dta, data);
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._dataScheme, dataScheme);
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._dataType, dataType);
            jobj.put(com.rbt.vrde.utils.R.schedule.Event._category, category);
        } catch (JSONException e) {
        }
        return jobj.toString();
    }
}
