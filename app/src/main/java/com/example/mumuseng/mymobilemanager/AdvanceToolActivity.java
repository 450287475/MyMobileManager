package com.example.mumuseng.mymobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

public class AdvanceToolActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_tool);
    }
    public void queryLocation(View v){
        startActivity(new Intent(this,QueryLocationActivity.class));
    }
}
