package com.example.beli.ui.homepage;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beli.R;
import com.example.beli.utils.SharedPreferencesUtil;

import org.w3c.dom.Text;

public class CheckHeartActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private SensorEventListener heartRateEventListener;
    private ImageView belilogo;
    private ProgressBar loading;
    private TextView loadingStatement;
    public static final String EXTRA_REPLY =
            "com.example.beli.extra.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkheart);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        loadingStatement = (TextView) findViewById(R.id.please_wait);
        belilogo = (ImageView) findViewById(R.id.belilogo);
        loading = (ProgressBar) findViewById(R.id.loading);

        if (heartRateSensor == null) {
            Toast.makeText(getApplicationContext(), "Mohon beri izin untuk body sensor", Toast.LENGTH_SHORT).show();
            finish();
        }

        heartRateEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                belilogo.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loadingStatement.setText("Tunggu dulu... Tahan jari Anda");
                if (event.values[0] > 0) {
                    SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil(getApplicationContext());
                    sharedPreferences.writeStringPreferences(EXTRA_REPLY, String.valueOf(Math.round(event.values[0])));
                    String nilai = String.valueOf(Math.round(event.values[0]));
                    Toast.makeText(getApplicationContext(), "Kecepatan detak jantung Anda " + nilai + " bpm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(heartRateEventListener, heartRateSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(heartRateEventListener);
    }
}
