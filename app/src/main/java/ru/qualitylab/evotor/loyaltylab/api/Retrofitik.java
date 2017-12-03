package ru.qualitylab.evotor.loyaltylab.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitik {

    private static final String BASE_URL = "http://ec2-52-15-164-219.us-east-2.compute.amazonaws.com/api/";

    private static Retrofit INSTANSE;

    private Retrofitik() {
        // Singleton
    }

    public synchronized static Retrofit getInstanse() {
        if (INSTANSE == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            INSTANSE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            return INSTANSE;
        } else {
            return INSTANSE;
        }
    }

}
