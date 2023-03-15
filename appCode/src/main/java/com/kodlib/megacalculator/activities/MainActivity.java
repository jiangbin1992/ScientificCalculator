package com.kodlib.megacalculator.activities;

import static com.best.now.myad.utils.Constant.URL_PRIVACY_POLICY;
import static com.best.now.myad.utils.PublicHelperKt.isRewarded;
import static com.best.now.myad.utils.PublicHelperKt.loadAd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.best.now.myad.WebActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;

public class MainActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        LinearLayout advBanner = findViewById(R.id.advBanner);
        loadAd(advBanner);
        TextView PrivacyPolicy = findViewById(R.id.PrivacyPolicy);
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.Companion.startActivity(MainActivity.this, "Privacy Policy", URL_PRIVACY_POLICY);
            }
        });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdsUtility.loadInterstitialAd(this);

        FrameLayout nativeFrameTop = findViewById(R.id.nativeFrameTop);
        AdsUtility.loadNativeAd(MainActivity.this, nativeFrameTop, R.layout.ad_unified_med);

    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnNormalCalculator) {
            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, StandardCal.class);
                        startActivity(i);
                    }
                });
            }
        } else if (view.getId() == R.id.btnScientificCal) {
            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, ScientificCal.class);
                        startActivity(i);
                    }
                });
            }
        } else if (view.getId() == R.id.btnUnitCal) {
            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, UnitConverter.class);
                        startActivity(i);
                    }
                });
            }
        } else if (view.getId() == R.id.btnAgeCal) {
            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, AgeCalculator.class);
                        startActivity(i);
                    }
                });
            }

        } else if (view.getId() == R.id.btnBMICal) {
            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, BMICalculator.class);
                        startActivity(i);
                    }
                });
            }
        } else if (view.getId() == R.id.btnEMI) {

            if (isRewarded(MainActivity.this)) {
                AdsUtility.showInterAds(MainActivity.this, new AdsUtility.AdFinished() {
                    @Override
                    public void onAdFinished() {
                        Intent i;
                        i = new Intent(MainActivity.this, EMIActivity.class);
                        startActivity(i);
                    }
                });
            }
        }

    }

    @Override
    public void onBackPressed() {
        showBackDialog();
    }

    public void showBackDialog() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Double tap to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

}