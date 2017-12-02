package ru.qualitylab.evotor.loyaltylab.util;

import android.annotation.SuppressLint;
import android.util.Log;

public class Logger {

    private static final String TAG = "ru.qualitylab.evotor.loyaltylab";

    @SuppressLint("LongLogTag")
    public static void log(String text){
        Log.e(TAG, text);
    }
}
