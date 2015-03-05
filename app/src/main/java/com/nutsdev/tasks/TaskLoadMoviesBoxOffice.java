package com.nutsdev.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.nutsdev.callbacks.BoxOfficeMoviesLoadedListener;
import com.nutsdev.extras.MovieUtils;
import com.nutsdev.network.VolleySingleton;
import com.nutsdev.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by user on 05.03.15.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        ArrayList<Movie> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if (myComponent != null) {
            myComponent.onBoxOfficeMoviesLoaded(movies);
        }
    }

}
