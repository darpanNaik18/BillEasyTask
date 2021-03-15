package com.billeasytest.Retrofit;

import com.billeasytest.Model.NowPlayingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET("movie/now_playing")
    Call<NowPlayingResponse> nowPlaying(@Query("api_key") String apiKey, @Query("page") String page);

}
