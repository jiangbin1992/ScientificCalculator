package com.kodlib.megacalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.AdsUtility;
import com.kodlib.megacalculator.utils.DBHelper;
import com.kodlib.megacalculator.utils.ExtendedDoubleEvaluator;

public class StandardCal extends AppCompatActivity {


    private EditText e1, e2;
    private int count = 0;
    private String expression = "";
    private String text = "";
    private Double result = 0.0;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_standard_cal);


        e1 = (EditText) findViewById(R.id.editText1);
        e2 = (EditText) findViewById(R.id.editText2);
        dbHelper = new DBHelper(this);


        LinearLayout banner = findViewById(R.id.nativeFrameStd);

        AdsUtility.loadAdmobBanner(this, banner);


        ScrollView scrollView = findViewById(R.id.scrollET2);
        scrollView.fullScroll(View.FOCUS_DOWN);


    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            super.onBackPressed();
        } else if (id == R.id.num0) {
            e2.setText(e2.getText() + "0");
        } else if (id == R.id.num1) {
            e2.setText(e2.getText() + "1");
        } else if (id == R.id.num2) {
            e2.setText(e2.getText() + "2");
        } else if (id == R.id.num3) {
            e2.setText(e2.getText() + "3");
        } else if (id == R.id.num4) {
            e2.setText(e2.getText() + "4");
        } else if (id == R.id.num5) {
            e2.setText(e2.getText() + "5");
        } else if (id == R.id.num6) {
            e2.setText(e2.getText() + "6");
        } else if (id == R.id.num7) {
            e2.setText(e2.getText() + "7");
        } else if (id == R.id.num8) {
            e2.setText(e2.getText() + "8");
        } else if (id == R.id.num9) {
            e2.setText(e2.getText() + "9");
        } else if (id == R.id.dot) {
            if (count == 0 && e2.length() != 0) {
                e2.setText(e2.getText() + ".");
                count++;
            }
        } else if (id == R.id.clear) {
            e1.setText("");
            e2.setText("");
            count = 0;
            expression = "";
        } else if (id == R.id.backSpace) {
            text = e2.getText().toString();
            if (text.length() > 0) {
                if (text.endsWith(".")) {
                    count = 0;
                }
                String newText = text.substring(0, text.length() - 1);
                //to delete the data contained in the brackets at once
                if (text.endsWith(")")) {
                    char[] a = text.toCharArray();
                    int pos = a.length - 2;
                    int counter = 1;
                    //to find the opening bracket position
                    for (int i = a.length - 2; i >= 0; i--) {
                        if (a[i] == ')') {
                            counter++;
                        } else if (a[i] == '(') {
                            counter--;
                        }
                        //if decimal is deleted b/w brackets then count should be zero
                        else if (a[i] == '.') {
                            count = 0;
                        }
                        //if opening bracket pair for the last bracket is found
                        if (counter == 0) {
                            pos = i;
                            break;
                        }
                    }
                    newText = text.substring(0, pos);
                }
                //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
                if (newText.equals("-") || newText.endsWith("sqrt")) {
                    newText = "";
                }
                //if pow sign is left at the last
                else if (newText.endsWith("^"))
                    newText = newText.substring(0, newText.length() - 1);

                e2.setText(newText);
            }
        } else if (id == R.id.plus) {
            operationClicked("+");
        } else if (id == R.id.minus) {
            operationClicked("-");
        } else if (id == R.id.divide) {
            operationClicked("/");
        } else if (id == R.id.multiply) {
            operationClicked("*");
        } else if (id == R.id.sqrt) {
            if (e2.length() != 0) {
                text = e2.getText().toString();
                e2.setText("sqrt(" + text + ")");
            }
        } else if (id == R.id.square) {
            if (e2.length() != 0) {
                text = e2.getText().toString();
                e2.setText("(" + text + ")^2");
            }
        } else if (id == R.id.posneg) {
            if (e2.length() != 0) {
                String s = e2.getText().toString();
                char arr[] = s.toCharArray();
                if (arr[0] == '-')
                    e2.setText(s.substring(1, s.length()));
                else
                    e2.setText("-" + s);
            }
        } else if (id == R.id.equal) {
            if (e2.length() != 0) {
                text = e2.getText().toString();
                expression = e1.getText().toString() + text;
            }
            e1.setText("");
            if (expression.length() == 0)
                expression = "0.0";
            DoubleEvaluator evaluator = new DoubleEvaluator();
            try {
                //evaluate the expression
                result = new ExtendedDoubleEvaluator().evaluate(expression);
                //insert expression and result in sqlite database if expression is valid and not 0.0
                if (!expression.equals("0.0"))
                    dbHelper.insert("STANDARD", expression + " = " + result);
                e2.setText(result + "");
            } catch (Exception e) {
                e2.setText("Invalid Expression");
                e1.setText("");
                expression = "";
                e.printStackTrace();
            }
        } else if (id == R.id.openBracket) {
            e1.setText(e1.getText() + "(");
        } else if (id == R.id.closeBracket) {
            e1.setText(e1.getText() + ")");
        } else if (id == R.id.history) {
            Intent i = new Intent(this, History.class);
            i.putExtra("calcName", "STANDARD");
            startActivity(i);
        }
    }

    private void operationClicked(String op) {
        if (e2.length() != 0) {
            String text = e2.getText().toString();
            e1.setText(e1.getText() + text + op);
            e2.setText("");
            count = 0;
        } else {
            String text = e1.getText().toString();
            if (text.length() > 0) {
                String newText = text.substring(0, text.length() - 1) + op;
                e1.setText(newText);
            }
        }
    }


}