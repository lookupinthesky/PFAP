package com.example.pfa_p;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;

import okhttp3.OkHttpClient;
import timber.log.Timber;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        AndroidNetworking.initialize(getApplicationContext());
        Timber.plant(new Timber.DebugTree());
        /*OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);*/
    }
}
