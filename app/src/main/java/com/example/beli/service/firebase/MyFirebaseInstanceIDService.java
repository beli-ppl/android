package com.example.beli.service.firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.beli.utils.SharedPreferencesUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        sharedPreferencesUtil.writeStringPreferences("FIREBASE_TOKEN", refreshedToken);
    }
}
