package com.wikidemo.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wikidemo.model.WikiResponse;
import com.wikidemo.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by winnerkm on 19/09/17.
 */

public class ApiClient {

    public static final String TAG = ApiClient.class.getSimpleName();
    public static final String BASE_URL = "https://en.wikipedia.org//w/";

    private APIService mApiService;

    private static ApiClient instance;
    private static Retrofit retrofit;
    private Context mContext;

    private ApiClient(Context context) {
        mContext = context.getApplicationContext();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApiService = getAPIServiceEndPoint();
    }

    public APIService getAPIServiceEndPoint() {
        if (mApiService == null) {
            mApiService = retrofit.create(APIService.class);
        }
        return mApiService;
    }

    public static ApiClient getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClient(context);
        }
        return instance;
    }

    public interface APIService {

        @GET("api.php")
        Call<WikiResponse> getWikiData(
                @Query("action") String action,
                @Query("format") String format,
                @Query("prop") String prop,
                @Query("generator") String generator,
                @Query("redirects") int redirects,
                @Query("formatversion") int formatversion,
                @Query("piprop") String piprop,
                @Query("pithumbsize") int pithumbsize,
                @Query("pilimit") int pilimit,
                @Query("wbptterms") String wbptterms,
                @Query("gpssearch") String gpssearch,
                @Query("gpslimit") int gpslimit);
    }

    public void apiCall(String query) {

        Call<WikiResponse> call = mApiService.getWikiData("query", "json", "pageimages|pageterms",
                "prefixsearch", 1, 2, "thumbnail", 500, 1000, "description", query, 10);


        Log.d(TAG, call.request().url().toString());


        call.enqueue(new Callback<WikiResponse>() {
            @Override
            public void onResponse(Call<WikiResponse> call, Response<WikiResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    onApiFailure(response.errorBody(), Constants.SomethingWentWrong);
                }
            }

            @Override
            public void onFailure(Call<WikiResponse> call, Throwable t) {
                onApiFailure(null, t.getMessage());
            }
        });
    }

    private void onApiFailure(ResponseBody response, String message) {
        if (response != null) {
            Log.d(TAG, response.toString());
        }
        EventBus.getDefault().post(new ErrorModel(message));
    }
}
