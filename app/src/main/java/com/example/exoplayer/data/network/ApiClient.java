package com.example.exoplayer.data.network;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "192.68.172.1/";

    public static Retrofit retrofit = null;

    private ApiInterface apiInterface;

    @Inject
    public ApiClient(){

    }

    public ApiInterface createApiInterface(){
        apiInterface = getClient().create(ApiInterface.class);
        return apiInterface;
    }

    private static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
