package com.example.mumuseng.mymobilemanager;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mumuseng.dao.NumberLocationDao;

public class QueryLocationActivity extends ActionBarActivity {

    private EditText et_queryLocation_number;
    private TextView tv_queryLocation_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_location);
        et_queryLocation_number = (EditText) findViewById(R.id.et_queryLocation_number);
        tv_queryLocation_location = (TextView) findViewById(R.id.tv_queryLocation_location);
    }
    public void query(View v){
        String s = et_queryLocation_number.getText().toString();
        String location = NumberLocationDao.getLocation(s);
        System.out.println(location);
        tv_queryLocation_location.setText(location);
    }
}
