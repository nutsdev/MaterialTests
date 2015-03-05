package com.nutsdev.services;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.nutsdev.extras.Constants;
import com.nutsdev.logging.L;
import com.nutsdev.materialtest.MyApplication;
import com.nutsdev.network.VolleySingleton;
import com.nutsdev.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_CAST;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_LINKS;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_SELF;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_SIMILAR;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.nutsdev.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static com.nutsdev.extras.UrlEndpoints.URL_BOX_OFFICE;
import static com.nutsdev.extras.UrlEndpoints.URL_CHAR_AMPERSAND;
import static com.nutsdev.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.nutsdev.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static com.nutsdev.extras.UrlEndpoints.URL_PARAM_LIMIT;

/**
 * Created by user on 27.02.15.
 */
public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        new MyTask(this).execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class MyTask extends AsyncTask<JobParameters, Void, JobParameters> {

        private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // volley singleton
        private VolleySingleton volleySingleton;
    //    private ImageLoader imageLoader;
        private RequestQueue requestQueue;

        MyService myService;

        MyTask(MyService myService) {
            this.myService = myService;
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            JSONObject response = sendJsonRequest();
            ArrayList<Movie> listMovies = parseJsonResponse(response);

            MyApplication.getWritableDatabase().insertMoviesBoxOffice(listMovies, false);
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            myService.jobFinished(jobParameters, false);
        }

        private JSONObject sendJsonRequest() {
            JSONObject response = null;
            RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(30), null, requestFuture, requestFuture);

            requestQueue.add(request);
            try {
                response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                L.m(e + "");
            } catch (ExecutionException e) {
                e.printStackTrace();
                L.m(e + "");
            } catch (TimeoutException e) {
                e.printStackTrace();
                L.m(e + "");
            }
            return response;
        }

        public static String getRequestUrl(int limit) {
            return URL_BOX_OFFICE
                    + URL_CHAR_QUESTION
                    + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                    + URL_CHAR_AMPERSAND
                    + URL_PARAM_LIMIT + limit;
        }

        private ArrayList<Movie> parseJsonResponse(JSONObject response) {
            ArrayList<Movie> listMovies = new ArrayList<>();

            if (response != null || response.length() > 0) {

                try {
                    JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);

                    for (int i = 0; i < arrayMovies.length(); i++) {
                        long id = -1;
                        String title = Constants.NA;
                        String releaseDate = Constants.NA;
                        int audienceScore = -1;
                        String synopsis = Constants.NA;
                        String urlThumbnail = Constants.NA;
                        String urlSelf = Constants.NA;
                        String urlCast = Constants.NA;
                        String urlReviews = Constants.NA;
                        String urlSimilar = Constants.NA;

                        JSONObject currentMovie = arrayMovies.getJSONObject(i);

                        // get the id of the current movie
                        if (contains(currentMovie, KEY_ID))
                            id = currentMovie.getLong(KEY_ID);

                        // get the title of the current movie
                        if (contains(currentMovie, KEY_TITLE))
                            title = currentMovie.getString(KEY_TITLE);

                        // get date in theatre
                        if (contains(currentMovie, KEY_RELEASE_DATES)) {
                            JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);
                            if (contains(objectReleaseDates, KEY_THEATER)) {
                                releaseDate = objectReleaseDates.getString(KEY_THEATER);
                            }
                        }

                        //get the audience score for the current movie
                        if (contains(currentMovie, KEY_RATINGS)) {
                            JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                            if (contains(objectRatings, KEY_AUDIENCE_SCORE)) {
                                audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                            }
                        }

                        //get the synopsis
                        if (contains(currentMovie, KEY_SYNOPSIS))
                            synopsis = currentMovie.getString(KEY_SYNOPSIS);

                        if (contains(currentMovie, KEY_POSTERS)) {
                            JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);
                            if (contains(objectPosters, KEY_THUMBNAIL)) {
                                urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                            }
                        }

                        // get the url of the related links
                        if (contains(currentMovie, KEY_LINKS)) {
                            JSONObject objectLinks = currentMovie.getJSONObject(KEY_LINKS);
                            if (contains(objectLinks, KEY_SELF)) {
                                urlSelf = objectLinks.getString(KEY_SELF);
                            }
                            if (contains(objectLinks, KEY_CAST)) {
                                urlCast = objectLinks.getString(KEY_CAST);
                            }
                            if (contains(objectLinks, KEY_REVIEWS)) {
                                urlReviews = objectLinks.getString(KEY_REVIEWS);
                            }
                            if (contains(objectLinks, KEY_SIMILAR)) {
                                urlSimilar = objectLinks.getString(KEY_SIMILAR);
                            }
                        }

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        Date date = null;
                        try { // todo resolve Exception!
                            date = dateFormat.parse(releaseDate);
                        } catch (ParseException e) {
                        //    L.t(getActivity(), "PARSE EXCEPTION CATCHED!");
                        }
                        movie.setReleaseDateTheater(date);
                        movie.setAudienceScore(audienceScore);
                        movie.setSynopsis(synopsis);
                        movie.setUrlThumbnail(urlThumbnail);

                        if (id != -1 && !title.equals(Constants.NA)) {
                            listMovies.add(movie);
                        }
                    }
                //    L.t(getActivity(), listMovies.size() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return listMovies;
        }

        private boolean contains(JSONObject jsonObject, String key) {
            return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
        }

    }

}
