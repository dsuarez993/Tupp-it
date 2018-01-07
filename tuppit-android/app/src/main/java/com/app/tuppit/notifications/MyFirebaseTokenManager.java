package com.app.tuppit.notifications;

import android.util.Log;

import com.app.tuppit.api.ServiceAPI;
import com.app.tuppit.api.ServiceAPIHelper;
import com.app.tuppit.api.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 18/10/17.
 */

public class MyFirebaseTokenManager {

    public static void updateTokenId(String userId, String tokenId){

        // Getting a JSON object
        ServiceAPI api = ServiceAPIHelper.getRestClient();
        Call<User> callObject = api.updateTokenId(userId, tokenId);
        callObject.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    try {
                        User user = response.body();
                        Log.i("User Call", "" + user.data.tokenId);

                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User Call", t.getLocalizedMessage());

            }
        });

    }
}
