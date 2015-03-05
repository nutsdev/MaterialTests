package com.nutsdev.json;

import com.nutsdev.materialtest.MyApplication;

import static com.nutsdev.extras.UrlEndpoints.URL_BOX_OFFICE;
import static com.nutsdev.extras.UrlEndpoints.URL_CHAR_AMPERSAND;
import static com.nutsdev.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.nutsdev.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static com.nutsdev.extras.UrlEndpoints.URL_PARAM_LIMIT;

/**
 * Created by user on 05.03.15.
 */
public class Endpoints {

    public static String getRequestUrl(int limit) {
        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMPERSAND
                + URL_PARAM_LIMIT + limit;
    }

}
