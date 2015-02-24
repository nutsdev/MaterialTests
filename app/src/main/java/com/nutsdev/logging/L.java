package com.nutsdev.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by n1ck on 23.02.2015.
 */
public class L {

    public static void m(String message) {
        Log.d("nutsdev", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

}
