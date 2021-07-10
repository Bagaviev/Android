package com.example.fuckingretrofit.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
//    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String BASE_URL = "https://reqres.in/api/";

    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()      // тут билдер для gson отдельный не нужен
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public OurAPI getJSONApi() {
        return mRetrofit.create(OurAPI.class);
    }
}
