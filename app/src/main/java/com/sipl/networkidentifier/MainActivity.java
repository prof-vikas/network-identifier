package com.sipl.networkidentifier;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sipl.networkidentifier.dto.response.UserResponseDto;
import com.sipl.networkidentifier.receiver.NetworkChangeReceiver;
import com.sipl.networkidentifier.retrofit.RetrofitController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTag";

    private Vibrator vibrator;
    TextView txtRawApi, txtUsername, txtEmail, txtStatus, txtHeaderWifiNet;
    Button btnCallApi, btnClear;

    NetworkChangeReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtRawApi = findViewById(R.id.txt_raw_url);
        txtUsername = findViewById(R.id.txt_user_name);
        txtEmail = findViewById(R.id.txt_email);
        txtStatus = findViewById(R.id.txt_network_mode);
        txtHeaderWifiNet = findViewById(R.id.header_wifi);

        btnCallApi = findViewById(R.id.btn_call_api);
        btnClear = findViewById(R.id.btn_clear);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        receiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate(100);
                txtEmail.setText("");
                txtRawApi.setText("");
                txtUsername.setText("");
            }
        });

        btnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate(200);
                getUserById();
            }
        });
    }

    private void vibrate(long milliseconds) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
    }

    public void setNetworkMode(String mode, String header){
        txtStatus.setText(mode);
        txtHeaderWifiNet.setText(header);
        txtEmail.setText("");
        txtRawApi.setText("");
        txtUsername.setText("");
    }

    private void getUserById() {
        Call callForWifiNetworkApi = RetrofitController.getInstance().getRetrofitApiWifiNet().getUserById("1");
        Call callForMobNetworkApi = RetrofitController.getInstance().getRetrofitApiMobileNet().getUserById("1");
        Call<UserResponseDto> call;

        if (receiver.isWifiEnable == true){
            call = callForWifiNetworkApi;
        }else {
            call = callForMobNetworkApi;
        }
        call.enqueue(new Callback<UserResponseDto>() {
            @Override
            public void onResponse(Call<UserResponseDto> call, Response<UserResponseDto> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:  response.errorBody : " + response.errorBody());
                }
                if (response.isSuccessful()) {
                    txtEmail.setText(response.body().getEmail());
                    txtUsername.setText(response.body().getUsername());
                    txtRawApi.setText(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<UserResponseDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}