package com.app.tuppit.notifications;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by david on 17/10/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private String TAG = "FirebaseIDService";

    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        //sendRegistrationToServer(refreshedToken);
        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        String userId = settings.getString("user", "");

        MyFirebaseTokenManager.updateTokenId(userId,refreshedToken);

    }

}