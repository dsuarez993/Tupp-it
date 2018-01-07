package com.example.david.apitest.api;

import android.content.Context;

import com.example.david.apitest.utils.App;
import com.example.david.apitest.utils.SelfSigningClientBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 14/5/17.
 */



public class ServiceAPIHelper {

    private static ServiceAPI api;

    static {
        setupRestClient();
    }

    private static void setupRestClient() {

        // Define our client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.1.41:3000/")
                .client(SelfSigningClientBuilder.createClient(App.context()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ServiceAPI.class);
    }

    public static ServiceAPI getRestClient() {
        return api;
    }

}


