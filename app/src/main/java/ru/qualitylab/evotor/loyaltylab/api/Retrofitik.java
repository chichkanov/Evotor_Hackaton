package ru.qualitylab.evotor.loyaltylab.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitik {

    private static final String BASE_URL = "http://ec2-18-217-203-229.us-east-2.compute.amazonaws.com/api/";

    private static Retrofit INSTANSE;

    private Retrofitik() {
        // Singleton
    }

    public synchronized static Retrofit getInstanse() {
        if (INSTANSE == null) {
            INSTANSE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return INSTANSE;
        } else {
            return INSTANSE;
        }
    }

}
