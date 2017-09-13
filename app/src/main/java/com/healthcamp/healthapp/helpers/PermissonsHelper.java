package com.healthcamp.healthapp.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by ITH-143 on 29-Aug-17.
 */

public class PermissonsHelper extends Activity {
    public  boolean GrantPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= 23) { // Marshmallow

                    ActivityCompat
                            .requestPermissions(
                                    this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                }
            }
        }
        return true;
    }
}
