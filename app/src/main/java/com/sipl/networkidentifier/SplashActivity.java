package com.sipl.networkidentifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            Intent id = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(id);
            finish();
            progressBar.setVisibility(View.GONE);
        }, 2000);
    }
}