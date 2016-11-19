package com.example.agustin.laboratorio09;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mAceleration;
    private float aceleracion_maxima[];
    private float aceleracion_nueva[];
    private String fechaMaximaAceleracion;
    private Boolean actualizarX,actualizarY,actualizarZ;
    private long fecha;
    private TextView tvHoraMaxX,tvHoraMaxY,tvHoraMaxZ;
    private TextView tvMagnitudMaxX,tvMagnitudMaxY,tvMagnitudMaxZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargarVariables();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("registration_id", null);
        System.out.println("token: "+token);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private void cargarVariables(){
        tvHoraMaxX = (TextView) findViewById(R.id.tvHoraMaxX);
        tvHoraMaxY = (TextView) findViewById(R.id.tvHoraMaxY);
        tvHoraMaxZ = (TextView) findViewById(R.id.tvHoraMaxZ);
        tvMagnitudMaxX = (TextView) findViewById(R.id.tvMagnitudMaxX);
        tvMagnitudMaxY = (TextView) findViewById(R.id.tvMagnitudMaxY);
        tvMagnitudMaxZ = (TextView) findViewById(R.id.tvMagnitudMaxZ);
        aceleracion_maxima = new float[3];
        aceleracion_nueva = new float[3];
        aceleracion_maxima[0] = 0;
        aceleracion_maxima[1] = 0;
        aceleracion_maxima[2] = 0;
        actualizarZ=false;
        actualizarY=false;
        actualizarX=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        aceleracion_nueva[0] = event.values[0];
        aceleracion_nueva[1] = event.values[1];
        aceleracion_nueva[2] = event.values[2];
        if(aceleracion_nueva[0] > aceleracion_maxima[0]){
            aceleracion_maxima[0] = aceleracion_nueva[0];
            fecha = System.currentTimeMillis();
            actualizarX=true;
        }
        if(aceleracion_nueva[1] > aceleracion_maxima[1]){
            aceleracion_maxima[1] = aceleracion_nueva[1];
            fecha = System.currentTimeMillis();
            actualizarY=true;
        }
        if(aceleracion_nueva[2] > aceleracion_maxima[2]){
            aceleracion_maxima[2] = aceleracion_nueva[2];
            fecha = System.currentTimeMillis();
            actualizarZ=true;
        }
        actualizarInterfaz();
    }

    private void actualizarInterfaz(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        fechaMaximaAceleracion = sdf.format(fecha);
        if(actualizarX){
            tvHoraMaxX.setText(fechaMaximaAceleracion);
            tvMagnitudMaxX.setText(Float.toString(aceleracion_maxima[0]));
            actualizarX=false;
        }
        if(actualizarY){
            tvHoraMaxY.setText(fechaMaximaAceleracion);
            tvMagnitudMaxY.setText(Float.toString(aceleracion_maxima[1]));
            actualizarY=false;
        }
        if(actualizarZ){
            tvHoraMaxZ.setText(fechaMaximaAceleracion);
            tvMagnitudMaxZ.setText(Float.toString(aceleracion_maxima[2]));
            actualizarZ=false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mAceleration,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
