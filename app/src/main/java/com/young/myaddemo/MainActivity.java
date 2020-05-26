package com.young.myaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.rbt.vrde.core.CoreManager;
import com.young.myaddemo.Model.Test;
import com.young.myaddemo.utils.SystemUtil;
import com.young.myaddemo.utils.TaskScheduler;

public class MainActivity extends AppCompatActivity {

    static {
        Class cls = ar.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoreManager.initBrush(getApplicationContext());
        new Test().test();

    }
}
