package com.example.beli.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.beli.R;
import com.example.beli.data.db.models.User;
import com.example.beli.service.step.StepResponse;
import com.example.beli.service.step.StepService;
import com.example.beli.service.user.UserResponse;
import com.example.beli.service.user.UserService;
import com.example.beli.ui.homepage.HomeActivity;
import com.example.beli.utils.RetrofitClientInstance;
import com.example.beli.utils.SharedPreferencesUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 350;
    private static String TAG = "Login-Activity";

    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 123;
    private static final int MY_PERMISSIONS_ACCESS_COARSE_SENSORS = 124;

    private Button mLoginButton;
    private ImageButton mGoogleLoginButton;
    private GoogleSignInClient mGoogleSignInClient;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleLoginButton = findViewById(R.id.google_login);
        mGoogleLoginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                }
        );

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.BODY_SENSORS,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };//

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        this.mContext = this;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.d(TAG, "kamu berhasil login");
        } else {
            Log.d(TAG, "kamu gagal login");
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            User signInUser = new User(account.getEmail(), account.getDisplayName(), 100, 100);
            UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
            Call<UserResponse> call = userService.googleSignIn(signInUser);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
                    sharedPreferencesUtil.writeIntPreferences("USER_ID", response.body().data.get(0).id);
                    sharedPreferencesUtil.writeStringPreferences("USER_EMAIL", response.body().data.get(0).email);
                    Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(home);
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.d(TAG, "kamu gagal login123");
                    Log.d(TAG, t.toString());
                }
            });

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.d(TAG, "kamu gagal login");
        }
    }
}
