package com.example.codehack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import android.support.v4.app.*;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button backbtn;
    Toolbar toolbar;
    private ConstraintLayout mainpage;
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    /*private ViewPager mainPager;
    private SectionsPagerAdapter msection;
    private TableLayout mtablayout;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainpage=(ConstraintLayout)findViewById(R.id.mainpage);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager=(ViewPager)findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout=(TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        //getSupportActionBar().setDisplayHomeAsUpEnabled();
        /*mainPager=(ViewPager)findViewById(R.id.mainView);
        msection=new SectionsPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(msection);

        mtablayout=(TableLayout)findViewById(R.id.main_tabs);*/

        backbtn=(Button)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                //sendToStart();
                final Snackbar snack=Snackbar.make(mainpage,"Want to logout ?",3000).setAction("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setActionTextColor(Color.GREEN).setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.signOut();
                        sendToStart();
                    }
                }).setActionTextColor(Color.GREEN);
                snack.show();
            }
        });
    }
    public void sendToStart(){
        Intent newIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(newIntent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id)
        {
            case R.id.settings_option:
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_option:
                final Snackbar snack=Snackbar.make(mainpage,"Want to logout ?",3000).setAction("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //snack.dismiss();
                    }
                }).setActionTextColor(Color.GREEN).setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.signOut();
                        sendToStart();
                    }
                }).setActionTextColor(Color.GREEN);
                snack.show();
                Toast.makeText(this,"Logging out",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutus_option:Toast.makeText(this,"About Us clicked",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:finish();
        }
        return super.onOptionsItemSelected(item);
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
