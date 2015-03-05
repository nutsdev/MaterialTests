package com.nutsdev.callbacks;

import com.nutsdev.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by user on 05.03.15.
 */
public interface BoxOfficeMoviesLoadedListener {

    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);

}
