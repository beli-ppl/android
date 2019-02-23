package com.example.beli.service.stepCounter;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class StepCounterService extends Service implements SensorEventListener {
    public SensorManager sensorManager;
    public Sensor stepCounterSensor;
    public int stepCounter;
    public boolean serviceStopped;
    public Intent intent;
    // A string that identifies what kind of action is taking place.
    private static final String TAG = "StepService";
    public static final String BROADCAST_ACTION = "com.example.beli.mybroadcast";
    // Create a handler - that will be used to broadcast our data, after a specified amount of time.
    private final Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent activity_loginintent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepCounterSensor, 0);

        handler.removeCallbacks(updateBroadcastData);
        handler.post(updateBroadcastData);

        serviceStopped = false;

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Service", "Stop");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCounter = (int) event.values[0];
        Log.d(TAG, String.valueOf(stepCounter));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //will implemented
    }

    private Runnable updateBroadcastData = new Runnable() {
        public void run() {
            if (!serviceStopped) { // Only allow the repeating timer while service is running (once service is stopped the flag state will change and the code inside the conditional statement here will not execute).
                // Call the method that broadcasts the data to the Activity..
                broadcastSensorValue();
                // Call "handler.postDelayed" again, after a specified delay.
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void broadcastSensorValue() {
        intent.putExtra("Counted_Step", String.valueOf(stepCounter));
        Log.d(TAG, "send broadcast");

        sendBroadcast(intent);
    }
}
