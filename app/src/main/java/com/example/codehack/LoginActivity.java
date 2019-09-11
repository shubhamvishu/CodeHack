package com.example.codehack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView signup;
    private Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=(TextView)findViewById(R.id.signupbtn);
        signin=(Button)findViewById(R.id.signinbtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("D","signup pressed");
                Intent signup_intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signup_intent);
            }
        });

    }
}
