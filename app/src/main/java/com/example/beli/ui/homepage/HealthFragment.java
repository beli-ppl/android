package com.example.beli.ui.homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.example.beli.R;
import com.example.beli.utils.SharedPreferencesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;
import static com.example.beli.ui.homepage.CheckHeartActivity.EXTRA_REPLY;

public class HealthFragment extends Fragment {

    private TextView textLat;
    private TextView textLong;

    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 123;
    private static String TAG = "Health-Fragment";
    private Button checkButton;

    private FusedLocationProviderClient fusedLocationClient;

    public static final int TEXT_REQUEST = 1;
    private TextView heartratetext;

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
                                textLat.setText("lat: " + Double.toString(location.getAltitude()));
                                textLong.setText("long: " + Double.toString(location.getLongitude()));
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
                        Intent intent = new Intent(getActivity(), CheckHeartActivity.class);
                        getActivity().startActivityForResult(intent, TEXT_REQUEST);
                    }
                }
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferencesUtil sharedPreference = new SharedPreferencesUtil(getActivity());
        String yourheartrate = sharedPreference.readStringPreferences(EXTRA_REPLY);
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
}
