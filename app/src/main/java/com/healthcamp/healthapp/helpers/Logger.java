package com.healthcamp.healthapp.helpers;

import android.util.Log;

/**
 * Created by ITH-143 on 3/26/2017.
 */

public class Logger {
    static boolean debugMode = true;

    public static void e(String tag, String message) {
        if (debugMode) {
            Log.e(tag, message);
        }
    }
}
