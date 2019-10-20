package com.example.codehack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ContestPageActivity extends AppCompatActivity {

    public static Object contestObject;
    public static String link="";
    private Toolbar toolbar;
    private TextView sitename,contestname,start,end,duration;
    private ImageView contestimage;
    private Button contestbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_page);
        Log.d("MSG","IN CONTEST PAGE");
        Toast.makeText(getApplicationContext(),"Contest page",Toast.LENGTH_SHORT).show();
        toolbar=(Toolbar)findViewById(R.id.contestpage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contestname=(TextView)findViewById(R.id.contestpage_contest_name);
        sitename=(TextView)findViewById(R.id.contestpage_site_name);
        start=(TextView)findViewById(R.id.contestpage_start);
        end=(TextView)findViewById(R.id.contestpage_end);
        duration=(TextView)findViewById(R.id.contestpage_duration);
        contestimage=(ImageView)findViewById(R.id.contestpage_image);
        contestbtn=(Button)findViewById(R.id.contest_btn);

        if(contestObject!=null) {
            Log.d("insideeeeeee",contestObject.getStart().substring(contestObject.getStart().indexOf("T") + 1));
            contestname.setText(contestObject.getEvent());
            sitename.setText(contestObject.getResource().getName());
            start.setText(contestObject.getStart().substring(0, contestObject.getStart().indexOf("T")) + " at " + contestObject.getStart().substring(contestObject.getStart().indexOf("T") + 1));
            end.setText(contestObject.getEnd().substring(0, contestObject.getEnd().indexOf("T")) + " at "+ contestObject.getEnd().substring(contestObject.getEnd().indexOf("T") + 1));
            duration.setText(contestObject.getDuration() / 3600 + " hours");
            Glide.with(contestimage).load(link).into(contestimage);
        }
        contestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(contestObject.getHref()));
                    startActivity(i);
                }catch (Exception ex){
                    Log.d("MSG","Link could not open"+contestObject.getHref());
                }
            }
        });
    }
}
