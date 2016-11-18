package com.example.agustin.laboratorio09;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseService extends FirebaseInstanceIdService {
    public FirebaseService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onTokenRefresh() {
        // obtener token InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        saveTokenToPrefs(refreshedToken);
    }
    private void saveTokenToPrefs(String _token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
        // luego en cualquier parte de la aplicación podremos recuperar el token con
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // preferences.getString("registration_id", null);
    }

}
