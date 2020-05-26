package com.young.myaddemo.Model;

import android.content.Intent;

import java.util.HashMap;

public interface ICallBack {
    void callback(String action, HashMap<String, Object> data, Intent intent);
}
