package com.example.beli.ui.homepage;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.beli.utils.SharedPreferencesUtil;

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
    private ImageView gizi4;
    private TextView kaloriView;

    public GiziFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gizi, container, false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getActivity());

        kaloriView = (TextView) view.findViewById(R.id.kalori);
        counter = (TextView) view.findViewById(R.id.counter_gizi);
        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar_gizi);
        button = (Button) view.findViewById(R.id.kirimdata);
        gizi1 = (ImageView) view.findViewById(R.id.gizi1);
        gizi2 = (ImageView) view.findViewById(R.id.gizi2);
        gizi3 = (ImageView) view.findViewById(R.id.gizi3);
        gizi4 = (ImageView) view.findViewById(R.id.gizi4);
        Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
        if (kalori != null) {
            counter.setText(String.valueOf(kalori));
            progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
            kaloriView.setVisibility(View.VISIBLE);
        } else if (kalori >= 2500) {
            progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
        }

        button.setOnClickListener(this);
        gizi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 400;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                if (kalori >= 2500) {
                    progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
                }

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
                kaloriView.setVisibility(View.VISIBLE);
            }
        });
        gizi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 600;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                if (kalori >= 2500) {
                    progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
                }

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
                kaloriView.setVisibility(View.VISIBLE);
            }
        });
        gizi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 500;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                if (kalori >= 2500) {
                    progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
                }

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
                kaloriView.setVisibility(View.VISIBLE);
            }
        });
        gizi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
                if (kalori == null) {
                    kalori = 0;
                }
                kalori += 100;
                sharedPreferencesUtil.writeIntPreferences("KALORI", kalori);

                if (kalori >= 2500) {
                    progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
                }

                Log.d(TAG, String.valueOf(kalori));
                counter.setText(String.valueOf(kalori));
                progressBar.setProgress(Float.parseFloat(String.valueOf(counter.getText())));
                kaloriView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Integer kalori = sharedPreferencesUtil.readIntPreferences("KALORI");
        if (kalori >= 2500) {
            progressBar.setForegroundStrokeColor(Color.rgb(46,134,193));
        }
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
