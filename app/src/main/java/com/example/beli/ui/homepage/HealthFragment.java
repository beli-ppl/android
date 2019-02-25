package com.example.beli.ui.homepage;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
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
import com.example.beli.service.stepCounter.StepCounterService;
import com.example.beli.utils.SharedPreferencesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;

import static android.app.Activity.RESULT_OK;
import static com.example.beli.ui.homepage.CheckHeartActivity.EXTRA_REPLY;

public class HealthFragment extends Fragment implements View.OnClickListener {
    private TextView textLat;
    private TextView textLong;

    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 123;
    private static final int MY_PERMISSIONS_ACCESS_COARSE_SENSORS = 124;
    private static String TAG = "Health-Fragment";
    private Button checkButton;

    private FusedLocationProviderClient fusedLocationClient;

    public static final int TEXT_REQUEST = 1;
    private TextView heartratetext;
    private CircularProgressBar progressBar;
    private TextView counter;
    private Button button;

    private Intent mStepIntent;

    public HealthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStepIntent = new Intent(getActivity(), StepCounterService.class);
        getActivity().startService(mStepIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        View view = inflater.inflate(R.layout.fragment_health, container, false);

        textLat = (TextView) view.findViewById(R.id.location_lat);
        textLong = (TextView) view.findViewById(R.id.location_long);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                textLat.setText("Latitude " + new DecimalFormat("##.##").format(location.getAltitude()));
                                textLong.setText("Longitude " + new DecimalFormat("##.##").format(location.getLongitude()));
                            }
                        }
                    });
        }

        heartratetext = (TextView) view.findViewById(R.id.yourheartrate);

        checkButton = (Button) view.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkHeartRate();
                    }
                }
        );

        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        counter = (TextView) view.findViewById(R.id.counter);

        button = (Button) view.findViewById(R.id.kirimdata);
        button.setOnClickListener(this);

        SharedPreferencesUtil sharedPreference = new SharedPreferencesUtil(getActivity());
        String yourheartrate = sharedPreference.readStringPreferences(EXTRA_REPLY) + " bpm";
        Log.d("create", yourheartrate);
        if (yourheartrate.compareTo(" bpm") == 0) {
            Log.d("hello", "masuk");
            heartratetext.setText("Segera Periksa!");
        } else {
            heartratetext.setText(yourheartrate);
        }

        return view;
    }

    public void checkHeartRate() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.BODY_SENSORS}, MY_PERMISSIONS_ACCESS_COARSE_SENSORS);
        } else {
            Intent intent = new Intent(getActivity(), CheckHeartActivity.class);
            getActivity().startActivityForResult(intent, TEXT_REQUEST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferencesUtil sharedPreference = new SharedPreferencesUtil(getActivity());
        String yourheartrate = sharedPreference.readStringPreferences(EXTRA_REPLY) + " bpm";
        Log.d("resume", yourheartrate);
        if (yourheartrate.compareTo(" bpm") == 0) {
            Log.d("helloresume", "masuk");
            heartratetext.setText("Segera Periksa!");
        } else {
            heartratetext.setText(yourheartrate);
        }

        IntentFilter stepCounterFilter = new IntentFilter(StepCounterService.BROADCAST_ACTION);
        getActivity().registerReceiver(broadcastReceiver, stepCounterFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateViews(intent);
        }
    };

    private void updateViews(Intent intent) {
        String countedStep = intent.getStringExtra("Counted_Step");
        counter.setText(String.valueOf(countedStep));
        progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
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
            } case TEXT_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                        checkHeartRate();
                    } else {
                        Toast.makeText(getActivity(), "Anda belum memberikan izin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }
        }
    }

    public void onClick(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());
        String email = sharedPreferencesUtil.readStringPreferences("USER_EMAIL");
        Log.d("email",email);
        String subject = "Data Asupan Gizi";
        String langkah = String.valueOf(counter.getText());
        String heartrate = String.valueOf(heartratetext.getText());
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
