package com.kodlib.megacalculator.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;

public class BMICalculator extends AppCompatActivity {


    private final static String TAG = BMICalculator.class.getSimpleName();
    private final static String PREF_IS_METRIC = "system_of_unit";

    TextView txt_result_bmi;
    TextView txt_result_cat;
    AutoCompleteTextView txt_height;
    AutoCompleteTextView txt_weight;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);


        LinearLayout banner = findViewById(R.id.bannerLinear);
        AdsUtility.loadAdmobBanner(this, banner);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_weight);
        initTextField(txt_height);
        initTextField(txt_weight);

        txt_result_bmi = findViewById(R.id.txt_result_bmi);
        txt_result_cat = findViewById(R.id.txt_result_cat);

        setSystemOfUnits();


    }

    private void initTextField(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateBmiIfPossible();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @SuppressLint("StringFormatMatches")
    private void calculateBmiIfPossible() {
        if (isValidInput(txt_height) && isValidInput(txt_weight)) {
            double bmi = calculateBmiAndCastIfNeeded(getTextAsDouble(txt_height), getTextAsDouble(txt_weight));
            txt_result_bmi.setText(getString(R.string.bmi_result, bmi));
            txt_result_cat.setText(getCategory(bmi));
        } else {
            txt_result_bmi.setText("");
            txt_result_cat.setText("");
        }
    }

    private boolean isValidInput(EditText editText) {
        return getTextAsDouble(editText) > 0;
    }

    private double getTextAsDouble(EditText editText) {
        String input = editText.getText().toString().replace(',', '.');
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double calculateBmiAndCastIfNeeded(double height, double weight) {
        height = isMetric() ? height : height / 39.37008;
        weight = isMetric() ? weight : weight / 2.204623;
        return calculateBmi(height, weight);
    }

    public static double calculateBmi(double height, double weight) {
        return Math.round(weight / Math.pow(height, 2) * 10d) / 10d;
    }

    private String getCategory(double bmi) {
        if (bmi < 15) {
            return getString(R.string.bmi_cat_1);
        }
        if (bmi < 16) {
            return getString(R.string.bmi_cat_2);
        }
        if (bmi < 18.5) {
            return getString(R.string.bmi_cat_3);
        }
        if (bmi < 25) {
            return getString(R.string.bmi_cat_4);
        }
        if (bmi < 30) {
            return getString(R.string.bmi_cat_5);
        }
        if (bmi < 35) {
            return getString(R.string.bmi_cat_6);
        }
        if (bmi < 40) {
            return getString(R.string.bmi_cat_7);
        }
        if (bmi < 45) {
            return getString(R.string.bmi_cat_8);
        }
        if (bmi < 50) {
            return getString(R.string.bmi_cat_9);
        }
        if (bmi < 60) {
            return getString(R.string.bmi_cat_10);
        }
        return getString(R.string.bmi_cat_11);
    }

    private void setSystemOfUnits() {
        RadioButton btn_metric = findViewById(R.id.btn_metric);
        RadioButton btn_imperial = findViewById(R.id.btn_imperial);
        btn_metric.setChecked(isMetric());
        btn_imperial.setChecked(!isMetric());


        AutoCompleteTextView height = findViewById(R.id.txt_height);
        AutoCompleteTextView weight = findViewById(R.id.txt_weight);

        if (isMetric()){
            btn_metric.setBackgroundResource(R.drawable.gr_orange);
            btn_metric.setTextColor(getResources().getColor(R.color.white));
            btn_metric.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

            btn_imperial.setBackgroundResource(R.color.transparent);
            btn_imperial.setTextColor(getResources().getColor(R.color.dark));
            btn_imperial.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark)));


        } else {

            btn_metric.setBackgroundResource(R.color.transparent);
            btn_metric.setTextColor(getResources().getColor(R.color.dark));
            btn_metric.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark)));

            btn_imperial.setBackgroundResource(R.drawable.gr_orange);
            btn_imperial.setTextColor(getResources().getColor(R.color.white));
            btn_imperial.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }




        weight.setHint(isMetric() ? getString(R.string.weight_metric) : getString(R.string.weight_imperial));
        height.setHint(isMetric() ? getString(R.string.height_metric) : getString(R.string.height_imperial));
    }

    private boolean isMetric() {
        boolean defaultToMetric = getString(R.string.default_unit).equals(getString(R.string.metric));
        return sharedPreferences.getBoolean(PREF_IS_METRIC, defaultToMetric);
    }

    public void setSystemOfUnits(View v) {
        sharedPreferences.edit().putBoolean(PREF_IS_METRIC, v.getId() == R.id.btn_metric).apply();
        setSystemOfUnits();
        calculateBmiIfPossible();
    }


    public void onClick(View view) {
        super.onBackPressed();
    }
}