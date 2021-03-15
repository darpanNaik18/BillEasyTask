package com.billeasytest.Retrofit;

import androidx.annotation.NonNull;

import com.billeasytest.Model.NowPlayingResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requestor {

    private final int code;
    private final ServerResponse serverResponse;
    public static APIServices apiServices;

    public Requestor(int code, ServerResponse serverResponse) {
        this.code = code;
        this.serverResponse = serverResponse;
        this.apiServices = RetrofitBase.getClient().create(APIServices.class);
    }

    @NonNull
    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        if (file == null) return null;
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void handleErrorRequest(int code) {
        switch (code) {
            case 400:
                serverResponse.error("No Result Found", this.code);
                break;
            case 404:
                serverResponse.error("Page Not found", this.code);
                break;
            default:
                serverResponse.error("Error", this.code);
                break;
        }
    }

    public void nowPlaying(String strApiKey, String strPage) {
        apiServices.nowPlaying(strApiKey, strPage)
                .enqueue(new Callback<NowPlayingResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NowPlayingResponse> call, @NonNull Response<NowPlayingResponse> response) {
                        if (response.code() == 200) {
                            serverResponse.success(response.body(), code);
                        } else handleErrorRequest(response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<NowPlayingResponse> call, @NonNull Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

}
