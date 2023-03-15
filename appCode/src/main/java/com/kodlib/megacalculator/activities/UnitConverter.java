package com.kodlib.megacalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;

public class UnitConverter extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);


        LinearLayout banner = findViewById(R.id.nativeFrameUnit);
        AdsUtility.loadAdmobBanner(this, banner);


    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.area) {
            AdsUtility.showInterAds(UnitConverter.this, new AdsUtility.AdFinished() {
                @Override
                public void onAdFinished() {
                    Intent i;
                    i = new Intent(UnitConverter.this, UnitArea.class);
                    startActivity(i);
                }
            });
        } else if (id == R.id.length) {
            AdsUtility.showInterAds(UnitConverter.this, new AdsUtility.AdFinished() {
                @Override
                public void onAdFinished() {
                    Intent i;
                    i = new Intent(UnitConverter.this, UnitLength.class);
                    startActivity(i);
                }
            });
        } else if (id == R.id.weight) {
            AdsUtility.showInterAds(UnitConverter.this, new AdsUtility.AdFinished() {
                @Override
                public void onAdFinished() {
                    Intent i;
                    i = new Intent(UnitConverter.this, UnitWeight.class);
                    startActivity(i);
                }
            });
        } else if (id == R.id.temperature) {
            AdsUtility.showInterAds(UnitConverter.this, new AdsUtility.AdFinished() {
                @Override
                public void onAdFinished() {
                    Intent i;
                    i = new Intent(UnitConverter.this, UnitTemperature.class);
                    startActivity(i);
                }
            });
        }
    }


}