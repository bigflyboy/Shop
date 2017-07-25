package com.visionin.shop.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.i("DemoLog","TestService -> onCreate, Thread ID: " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DemoLog", "TestService -> onStartCommand, startId: " + startId + ", Thread ID: " + Thread.currentThread().getId());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DemoLog", "TestService -> onBind, Thread ID: " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("DemoLog", "TestService -> onDestroy, Thread ID: " + Thread.currentThread().getId());
        super.onDestroy();
    }
}
