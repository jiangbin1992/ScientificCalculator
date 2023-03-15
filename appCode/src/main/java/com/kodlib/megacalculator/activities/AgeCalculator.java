package com.kodlib.megacalculator.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AgeCalculator extends AppCompatActivity
        implements View.OnClickListener {

    TextView textViewNextBirthdayMonths;
    TextView textViewNextBirthdayDays;
    TextView textViewFinalYears;
    TextView textViewFinalMonths;
    TextView textViewFinalDays;
    TextView textViewCurrentDay;
    RelativeLayout textViewCalculate;
    RelativeLayout textViewClear;
    ImageView imageViewCalenderFirst;
    ImageView imageViewCalenderSecond;
    EditText editTextBirthDay;
    EditText editTextBirthMonth;
    EditText editTextBirthYear;

    EditText editTextCurrentDay;

    EditText editTextCurrentMonth;

    EditText editTextCurrentYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_age_calculator);

         textViewNextBirthdayMonths =findViewById(R.id.textViewNextBirthdayMonths);
        textViewNextBirthdayDays=findViewById(R.id.textViewNextBirthdayDays);
        textViewFinalYears= findViewById(R.id.textViewFinalYears);
        textViewFinalMonths=findViewById(R.id.textViewFinalMonths);
        textViewFinalDays =findViewById(R.id.textViewFinalDays);
        textViewCurrentDay =findViewById(R.id.textViewCurrentDay);
        textViewCalculate =findViewById(R.id.textViewCalculate);
        textViewClear = findViewById(R.id.textViewClear);
        imageViewCalenderFirst = findViewById(R.id.imageViewCalenderFirst);
        imageViewCalenderSecond = findViewById(R.id.imageViewCalenderSecond);
        editTextBirthDay = findViewById(R.id.editTextBirthDay);
        editTextBirthMonth = findViewById(R.id.editTextBirthMonth);
        editTextBirthYear = findViewById(R.id.editTextBirthYear);
        editTextCurrentDay = findViewById(R.id.editTextCurrentDay);
        editTextCurrentMonth = findViewById(R.id.editTextCurrentMonth);
        editTextCurrentYear = findViewById(R.id.editTextCurrentYear);
        textViewCalculate.setOnClickListener(this);
        textViewClear.setOnClickListener(this);
        imageViewCalenderSecond.setOnClickListener(this);
        imageViewCalenderFirst.setOnClickListener(this);
        LinearLayout banner = findViewById(R.id.nativeFrameStd);
        AdsUtility.loadAdmobBanner(this, banner);
        setupCurrentDate(); // setup today's date


    }

    private void setupCurrentDate() {
        final Calendar c = Calendar.getInstance();
        editTextCurrentYear.setText(String.valueOf(c.get(Calendar.YEAR)));
        editTextCurrentMonth.setText(addZero(c.get(Calendar.MONTH) + 1));
        editTextCurrentDay.setText(addZero(c.get(Calendar.DAY_OF_MONTH)));

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) - 1);
        String dayOfWeek = simpledateformat.format(date);
        textViewCurrentDay.setText(dayOfWeek);
        textViewCurrentDay.setVisibility(View.VISIBLE);
    }

    private String addZero(int number) {
        String n;
        if (number < 10) {
            n = "0" + number;
        } else {
            n = String.valueOf(number);
        }
        return n;
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.backButton){
            super.onBackPressed();
        }


        if (view.getId() == R.id.imageViewCalenderSecond) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);






            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    editTextBirthDay.setText(addZero(dayOfMonth));
                    editTextBirthMonth.setText(addZero(monthOfYear + 1));
                    editTextBirthYear.setText(String.valueOf(year));
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (view == textViewCalculate) {
            if (!TextUtils.isEmpty(editTextBirthDay.getText()) && !TextUtils.isEmpty(editTextBirthMonth.getText()) && !TextUtils.isEmpty(editTextBirthYear.getText())) {
                calculateAge();
                nextBirthday();
            } else {
                Toasty.warning(AgeCalculator.this, "All fields are required", Toast.LENGTH_SHORT, true).show();
            }
        } else if (view == textViewClear) {
            editTextBirthDay.setText("");
            editTextBirthMonth.setText("");
            editTextBirthYear.setText("");
            Toasty.success(AgeCalculator.this, "Successfully reset", Toast.LENGTH_SHORT, true).show();

        }
    }


    private void nextBirthday() {
        int currentDay = Integer.valueOf(editTextCurrentDay.getText().toString());
        int currentMonth = Integer.valueOf(editTextCurrentMonth.getText().toString());
        int currentYear = Integer.valueOf(editTextCurrentYear.getText().toString());

        Calendar current = Calendar.getInstance();
        current.set(currentYear, currentMonth, currentDay);

        int birthDay = Integer.valueOf(editTextBirthDay.getText().toString());
        int birthMonth = Integer.valueOf(editTextBirthMonth.getText().toString());
        int birthYear = Integer.valueOf(editTextBirthYear.getText().toString());

        Calendar birthday = Calendar.getInstance();
        birthday.set(birthYear, birthMonth, birthDay);

        long difference = birthday.getTimeInMillis() - current.getTimeInMillis();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(difference);

        textViewNextBirthdayMonths.setText(String.valueOf(cal.get(Calendar.MONTH)));
        textViewNextBirthdayDays.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void calculateAge() {
        int currentDay = Integer.valueOf(editTextCurrentDay.getText().toString());
        int currentMonth = Integer.valueOf(editTextCurrentMonth.getText().toString());
        int currentYear = Integer.valueOf(editTextCurrentYear.getText().toString());

        Date now = new Date(currentYear, currentMonth, currentDay);

        int birthDay = Integer.valueOf(editTextBirthDay.getText().toString());
        int birthMonth = Integer.valueOf(editTextBirthMonth.getText().toString());
        int birthYear = Integer.valueOf(editTextBirthYear.getText().toString());

        Date dob = new Date(birthYear, birthMonth, birthDay);

        if (dob.after(now)) {
            Toasty.error(AgeCalculator.this, "Birthday can't in future", Toast.LENGTH_SHORT, true).show();
            return;
        }
        // days of every month
        int month[] = {31, 28, 31, 30, 31, 30, 31,
                31, 30, 31, 30, 31};

        // if birth date is greater then current birth
        // month then do not count this month and add 30
        // to the date so as to subtract the date and
        // get the remaining days
        if (birthDay > currentDay) {
            currentDay = currentDay + month[birthMonth - 1];
            currentMonth = currentMonth - 1;
        }

        // if birth month exceeds current month, then do
        // not count this year and add 12 to the month so
        // that we can subtract and find out the difference
        if (birthMonth > currentMonth) {
            currentYear = currentYear - 1;
            currentMonth = currentMonth + 12;
        }

        // calculate date, month, year
        int calculated_date = currentDay - birthDay;
        int calculated_month = currentMonth - birthMonth;
        int calculated_year = currentYear - birthYear;

        textViewFinalDays.setText(String.valueOf(calculated_date));
        textViewFinalMonths.setText(String.valueOf(calculated_month));
        textViewFinalYears.setText(String.valueOf(calculated_year));
    }


}