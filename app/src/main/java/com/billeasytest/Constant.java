package com.billeasytest;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.billeasytest.Retrofit.ServerCode;

public class Constant implements ServerCode {

    public interface TimeOut {
        int SOCKET_TIME_OUT = 60;
        int CONNECTION_TIME_OUT = 60;
    }

    public interface UrlPath {
        String BASE_URL = "https://api.themoviedb.org/3/";
        String MOVIEDB_API_KEY = "559ba5456b3d6bc02cb0523a1239d949";
        String MOVIEDB_POSTER_URL = "https://image.tmdb.org/t/p/original";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
