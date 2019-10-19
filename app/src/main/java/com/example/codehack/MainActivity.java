package com.example.codehack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import android.support.v4.app.*;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    Button backbtn;
    Toolbar toolbar;
    private ConstraintLayout mainpage;
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private static final String URL="https://clist.by:443/api/v1/contest/?start__gte=2019-10-19&limit=10&username=shubham13&api_key=f65ef54dd252de3177a053b85efd7817eb1cd169";
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

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        //getSupportActionBar().setDisplayHomeAsUpEnabled();
        /*mainPager=(ViewPager)findViewById(R.id.mainView);
        msection=new SectionsPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(msection);

        mtablayout=(TableLayout)findViewById(R.id.main_tabs);*/

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
                Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(i);
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_option:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to logout ?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        mAuth.signOut();
                                        sendToStart();
                                        Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.aboutus_option:
                Intent intent=new Intent(MainActivity.this,AboutusActivity.class);
                startActivity(intent);
                Toast.makeText(this,"About Us clicked",Toast.LENGTH_SHORT).show();
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
