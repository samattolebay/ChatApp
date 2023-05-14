package com.example.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.chatapp.R.layout;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_intro);
    }
}