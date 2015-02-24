package com.nutsdev.extras;

import android.os.Build;

/**
 * Created by n1ck on 23.02.2015.
 */
public class Util {

    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isJellyBeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16;
    }
}
