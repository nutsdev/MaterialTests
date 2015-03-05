package com.nutsdev.json;

import org.json.JSONObject;

/**
 * Created by user on 05.03.15.
 */
public class Utils {

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }

}
