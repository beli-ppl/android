package com.example.beli.ui.homepage;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.beli.R;
import com.example.beli.service.stepCounter.StepCounterService;
import com.example.beli.utils.SharedPreferencesUtil;

import java.util.Objects;

import static com.example.beli.ui.homepage.CheckHeartActivity.EXTRA_REPLY;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiziFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "GiziFragment";

    public CircularProgressBar progressBar;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private TextView counter;
    private Button button;
    private ImageView gizi1;
    private ImageView gizi2;
    private ImageView gizi3;

    public GiziFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gizi, container, false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());

        counter = (TextView) view.findViewById(R.id.counter_gizi);
        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar_gizi);
        button = (Button) view.findViewById(R.id.kirimdata);
        gizi1 = (ImageView) view.findViewById(R.id.gizi1);
        gizi2 = (ImageView) view.findViewById(R.id.gizi2);
        gizi3 = (ImageView) view.findViewById(R.id.gizi3);

        button.setOnClickListener(this);
        gizi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 50;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            }
        });
        gizi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 100;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            }
        });
        gizi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 80;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
