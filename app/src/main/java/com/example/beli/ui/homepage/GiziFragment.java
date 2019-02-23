package com.example.beli.ui.homepage;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.beli.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiziFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private CircularProgressBar progressBar;
    private TextView counter;
    boolean activityRunning;

    public GiziFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gizi, container, false);

        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        counter = (TextView) view.findViewById(R.id.counter);

        sensorManager = (SensorManager) (getActivity()).getSystemService(Context.SENSOR_SERVICE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            counter.setText(String.valueOf(event.values[0]));
            Log.d("stepcounter", String.valueOf(counter.getText()));
            progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            Log.d("stepprogress", String.valueOf(progressBar.getProgress()));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
