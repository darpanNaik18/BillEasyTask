package com.billeasytest;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billeasytest.Adapter.MoviesAdapter;
import com.billeasytest.Database.SQLite;
import com.billeasytest.Model.Movie;
import com.billeasytest.Model.NowPlayingResponse;
import com.billeasytest.Retrofit.Requestor;
import com.billeasytest.Retrofit.ServerResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServerResponse {

    private RecyclerView rvNowPlaying;
    private MoviesAdapter moviesAdapter;
    private List<Movie> movieList;

    private SQLite sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();

        if (Constant.isNetworkAvailable(getBaseContext())) {
            new Requestor(Constant.NOW_PLAYING, this).nowPlaying(Constant.UrlPath.MOVIEDB_API_KEY, "1");
        } else {
            movieList.clear();
            movieList.addAll(sqLite.getAllFavorite());
            moviesAdapter.notifyDataSetChanged();
        }
    }

    private void Initialize() {
        rvNowPlaying = findViewById(R.id.rvNowPlaying);
        rvNowPlaying.setLayoutManager(new GridLayoutManager(this, 2));

        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList);

        rvNowPlaying.setItemAnimator(new DefaultItemAnimator());
        rvNowPlaying.setAdapter(moviesAdapter);

        sqLite = new SQLite(this);
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.NOW_PLAYING:
                NowPlayingResponse nowPlayingResponse = (NowPlayingResponse) o;
                if (nowPlayingResponse != null) {
                    if (!nowPlayingResponse.getResults().isEmpty()) {
                        List<Movie> movies = nowPlayingResponse.getResults();
                        saveNowPlaying(movies);
                        rvNowPlaying.setAdapter(new MoviesAdapter(this, nowPlayingResponse.getResults()));
                    }
                } else {
                    movieList.clear();
                    movieList.addAll(sqLite.getAllFavorite());
                    moviesAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        movieList.clear();
        movieList.addAll(sqLite.getAllFavorite());
        moviesAdapter.notifyDataSetChanged();
    }

    private void saveNowPlaying(List<Movie> movies) {
        sqLite = new SQLite(this);

        Movie movie = new Movie();

        for (Movie m : movies) {
            movie.setId(m.getId());
            movie.setOriginalTitle(m.getOriginalTitle());
            movie.setOverview(m.getOverview());
            movie.setPoster_path(m.getPoster_path());
            movie.setRelease_date(m.getRelease_date());

            sqLite.addMovie(movie);
        }
    }

}