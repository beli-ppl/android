package com.example.beli.ui.homepage;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.beli.R;
import com.example.beli.service.stepCounter.StepCounterService;
import com.example.beli.utils.SharedPreferencesUtil;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiziFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "GiziFragment";

    public CircularProgressBar progressBar;

    private SensorManager sensorManager;
    private TextView counter;
    private Intent mStepIntent;
    private Button button;

    public GiziFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStepIntent = new Intent(getActivity(), StepCounterService.class);

        Log.d(TAG, "startService");
        getActivity().startService(new Intent(getActivity(), StepCounterService.class));
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
            Log.d(TAG, "On Receive");
            // call updateUI passing in our intent which is holding the data to display.
            updateViews(intent);
        }
    };

    private void updateViews(Intent intent) {
        String countedStep = intent.getStringExtra("Counted_Step");
        counter.setText(String.valueOf(countedStep));

        Log.d(TAG, String.valueOf(countedStep));
    }

    @Override
    public void onClick(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());
        String email = sharedPreferencesUtil.readStringPreferences("USER_EMAIL");
        Log.d("email",email);
        String subject = "Data Asupan Gizi";
        String kalori = String.valueOf(counter.getText());
        String bodyText = "Total kalori yang telah Anda akumulasi hari ini : " + kalori + " kcal";
        String mailto = "mailto:" + email + "?" +
                "subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(bodyText);
        Log.d("mailto", mailto);

        emailIntent.setData(Uri.parse(mailto));

        startActivity(emailIntent);
    }
}
