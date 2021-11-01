package com.example.bkob;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        //hide App bar
        getSupportActionBar().hide();
    }
}