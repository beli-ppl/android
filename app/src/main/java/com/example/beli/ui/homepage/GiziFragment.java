package com.example.beli.ui.homepage;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiziFragment extends Fragment implements View.OnClickListener {

    private TextView counter;
    private Button button;

    public GiziFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gizi, container, false);
        counter = (TextView) view.findViewById(R.id.counter);
        button = (Button) view.findViewById(R.id.kirimdata);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());
        String email = sharedPreferencesUtil.readStringPreferences("user_email");
        Log.d("email",email);
        String subject = "Data Asupan Gizi";
        String kalori = String.valueOf(counter.getText());
        String bodyText = "Total kalori yang telah Anda akumulasi hari ini : " + kalori;
        String mailto = "mailto:" + email + "?" +
                "subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(bodyText);
        Log.d("mailto", mailto);

        emailIntent.setData(Uri.parse(mailto));

        startActivity(emailIntent);
    }
}
