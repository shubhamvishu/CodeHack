package com.example.codehack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import android.support.v4.app.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button backbtn;
    Toolbar toolbar;
    private FirebaseAuth mAuth;

    /*private ViewPager mainPager;
    private SectionsPagerAdapter msection;
    private TableLayout mtablayout;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled();
        /*mainPager=(ViewPager)findViewById(R.id.mainView);
        msection=new SectionsPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(msection);

        mtablayout=(TableLayout)findViewById(R.id.main_tabs);*/

        backbtn=(Button)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent startIntent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
}
