package com.example.beli.ui.homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.beli.R;
import com.example.beli.utils.SharedPreferencesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;
import static android.app.Activity.RESULT_OK;
import static com.example.beli.ui.homepage.CheckHeartActivity.EXTRA_REPLY;

public class HealthFragment extends Fragment implements SensorEventListener, View.OnClickListener {
    private TextView textLat;
    private TextView textLong;

    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 123;
    private static final int MY_PERMISSIONS_ACCESS_COARSE_SENSORS = 124;
    private static String TAG = "Health-Fragment";
    private Button checkButton;

    private FusedLocationProviderClient fusedLocationClient;

    public static final int TEXT_REQUEST = 1;
    private TextView heartratetext;
    private SensorManager sensorManager;
    private CircularProgressBar progressBar;
    private TextView counter;
    private TextView yourheartrate;
    private Button button;
    boolean activityRunning;

    public HealthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        View view = inflater.inflate(R.layout.fragment_health, container, false);

        textLat = (TextView) view.findViewById(R.id.location_lat);
        textLong = (TextView) view.findViewById(R.id.location_long);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                textLat.setText("Latitude " + Double.toString(location.getAltitude()));
                                textLong.setText("Longitude " + Double.toString(location.getLongitude()));
                            }
                        }
                    });
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.BODY_SENSORS}, MY_PERMISSIONS_ACCESS_COARSE_SENSORS);
        } else {
        }

        heartratetext = (TextView) view.findViewById(R.id.yourheartrate);

        checkButton = (Button) view.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CheckHeartActivity.class);
                        getActivity().startActivityForResult(intent, TEXT_REQUEST);
                    }
                }
        );

        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        counter = (TextView) view.findViewById(R.id.counter);
        yourheartrate = (TextView) view.findViewById(R.id.yourheartrate);

        sensorManager = (SensorManager) (getActivity()).getSystemService(Context.SENSOR_SERVICE);
        button = (Button) view.findViewById(R.id.kirimdata);
        button.setOnClickListener(this);
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
            Toast.makeText(getActivity(), "Mohon beri izin untuk count sensor", Toast.LENGTH_LONG).show();
        }

        SharedPreferencesUtil sharedPreference = new SharedPreferencesUtil(getActivity());
        String yourheartrate = sharedPreference.readStringPreferences(EXTRA_REPLY) + " bpm";
        Log.d(TAG, yourheartrate);
        heartratetext.setText(yourheartrate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {
                                            Log.d(TAG, location.toString());
                                        }
                                    }
                                });
                    }
                } else {
                }
                return;
            }
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
            counter.setText(String.valueOf(Math.round(event.values[0])));
            Log.d("stepcounter", String.valueOf(counter.getText()));
            progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            Log.d("stepprogress", String.valueOf(progressBar.getProgress()));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onClick(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());
        String email = sharedPreferencesUtil.readStringPreferences("USER_EMAIL");
        Log.d("email",email);
        String subject = "Data Asupan Gizi";
        String langkah = String.valueOf(counter.getText());
        String heartrate = String.valueOf(yourheartrate.getText());
        String bodyText = "Total langkah Anda hari ini : " + langkah + " langkah";
        bodyText += "\n Detak jantung terakhir Anda : " + heartrate;
        String mailto = "mailto:" + email + "?" +
                "subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(bodyText);
        Log.d("mailto", mailto);

        emailIntent.setData(Uri.parse(mailto));

        startActivity(emailIntent);
    }
}
