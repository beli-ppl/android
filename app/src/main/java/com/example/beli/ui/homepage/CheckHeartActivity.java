package com.example.beli.ui.homepage;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beli.R;
import com.example.beli.utils.SharedPreferencesUtil;

import org.w3c.dom.Text;

public class CheckHeartActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private SensorEventListener heartRateEventListener;
    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 123;
    TextView showHeartRate;
    TextView loadingStatement;
    public static final String EXTRA_REPLY =
            "com.example.beli.extra.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkheart);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        loadingStatement = (TextView) findViewById(R.id.please_wait);
        showHeartRate = (TextView) findViewById(R.id.heartrate);

        if (heartRateSensor == null) {
            Toast.makeText(getApplicationContext(), "This device has no heart rate sensor", Toast.LENGTH_SHORT).show();
            finish();
        }

        heartRateEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                loadingStatement.setText("Hold still...");
                showHeartRate.setText(String.valueOf(event.values[0]));
                if (event.values[0] > 0) {
                    SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil(getApplicationContext());
                    sharedPreferences.writeStringPreferences(EXTRA_REPLY, String.valueOf(event.values[0]));
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
