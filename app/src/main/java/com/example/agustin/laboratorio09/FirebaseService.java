package com.example.agustin.laboratorio09;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;

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
        System.out.println("Token celu: "+refreshedToken);
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
