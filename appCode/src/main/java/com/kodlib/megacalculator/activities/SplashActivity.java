package com.kodlib.megacalculator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AdsUtility.loadInterstitialAd(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdsUtility.showInterAds(SplashActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }, 3000);
    }
}