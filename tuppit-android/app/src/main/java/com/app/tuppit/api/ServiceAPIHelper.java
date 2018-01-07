package com.app.tuppit.api;

import com.app.tuppit.R;
import com.app.tuppit.utils.App;
import com.app.tuppit.utils.SelfSigningClientBuilder;

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
                //.baseUrl(String.valueOf(R.string.server_ip))
                .client(SelfSigningClientBuilder.createClient(App.context()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ServiceAPI.class);
    }

    public static ServiceAPI getRestClient() {
        return api;
    }

}


