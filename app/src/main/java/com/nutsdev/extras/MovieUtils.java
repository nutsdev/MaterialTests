package com.nutsdev.extras;

import com.android.volley.RequestQueue;
import com.nutsdev.json.Endpoints;
import com.nutsdev.json.Parser;
import com.nutsdev.json.Requestor;
import com.nutsdev.materialtest.MyApplication;
import com.nutsdev.pojo.Movie;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 05.03.15.
 */
public class MovieUtils {

    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.sendRequestBoxOfficeMovies(requestQueue, Endpoints.getRequestUrl(30));
        ArrayList<Movie> listMovies = Parser.parseJsonResponse(response);
        MyApplication.getWritableDatabase().insertMoviesBoxOffice(listMovies, true);
        return listMovies;
    }

}
