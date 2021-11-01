package com.example.bkob.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bkob.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide App bar
        getSupportActionBar().hide();
    }
}