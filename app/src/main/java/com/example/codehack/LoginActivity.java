package com.example.codehack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout page;
    private TextView signup;
    private Button signin;
    private EditText username,password;
    private ProgressDialog loginProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        page=(RelativeLayout)findViewById(R.id.loginpage);
        signup=(TextView)findViewById(R.id.signupbtn);
        signin=(Button)findViewById(R.id.signinbtn);
        username=(EditText)findViewById(R.id.username_login);
        password=(EditText)findViewById(R.id.password_login);
        loginProgress=new ProgressDialog(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("D","signup pressed");
                Intent signup_intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signup_intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=username.getText().toString();
                String pass=password.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty() && email!=null && pass!=null){
                    loginProgress.setTitle("Logging In");
                    loginProgress.setMessage("Please wait...");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email,pass);
                }
                else{
                    //Toast.makeText(LoginActivity.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                    final Snackbar snack=Snackbar.make(page,"Empty Credentials",3000).setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(Color.GREEN);
                    snack.show();
                }
            }
        });
    }

    private void loginUser(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginProgress.dismiss();
                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    loginProgress.hide();
                    //Toast.makeText(LoginActivity.this,"Login failed.....Wrong username/password",Toast.LENGTH_SHORT).show();
                    final Snackbar snack=Snackbar.make(page,"Wrong username/password",3000).setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(Color.GREEN);
                    snack.show();
                }
            }
        });
    }
}
