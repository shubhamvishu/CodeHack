package com.example.codehack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class AboutusActivity extends AppCompatActivity {
    private Toolbar aboutus_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        aboutus_toolbar=(Toolbar)findViewById(R.id.aboutus_toolbar);
        setSupportActionBar(aboutus_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
