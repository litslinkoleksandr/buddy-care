package com.lits.buddycare.data.network;

import com.google.gson.GsonBuilder;
import com.lits.buddycare.BuildConfig;
import com.lits.buddycare.data.model.User;
import com.lits.buddycare.data.model.Wish;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by a.trykashnyi on 25.10.16.
 */

public interface ApiRestClient {

    @GET("wishes")
    Observable<List<Wish>> getWishes();
    @GET("users/{userId}")
    Observable<User> getUser(@Path("userId") int userId);

    class Creator {

        private static ApiRestClient instance;

        public static ApiRestClient getInstance() {
            if (instance == null) {
                instance = newApiRestClient();
            }
            return instance;
        }

        private static ApiRestClient newApiRestClient() {
            GsonBuilder gsonBuilder = new GsonBuilder();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .baseUrl(BuildConfig.ENDPOINT)
                    .client(httpClient.build())
                    .build();

            return retrofit.create(ApiRestClient.class);
        }
    }
}
