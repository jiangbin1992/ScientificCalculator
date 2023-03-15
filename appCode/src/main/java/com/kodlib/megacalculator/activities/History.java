package com.kodlib.megacalculator.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.kodlib.megacalculator.R;
import com.kodlib.megacalculator.utils.DBHelper;

import java.util.ArrayList;

public class History extends AppCompatActivity {


    private ListView lv;
    private DBHelper dbHelper;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private String calcName = "";
    private String[] EmptyList = {"There is  no history yet"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);


        lv = (ListView) findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        calcName = getIntent().getStringExtra("calcName");
        list = dbHelper.showHistory(calcName);
        if (!list.isEmpty())
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        else
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EmptyList);
        lv.setAdapter(adapter);


    }


    public void onClick(View v) {
        dbHelper.deleteRecords(calcName);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EmptyList);
        lv.setAdapter(adapter);
    }


}