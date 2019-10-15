package com.example.codehack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private RelativeLayout signup_page;
    private TextInputLayout sname;
    private TextInputLayout semail;
    private TextInputLayout spassword;
    private Button sbtn;
    private Toolbar toolbar;

    private ProgressDialog mprogress;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mprogress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        signup_page=(RelativeLayout)findViewById(R.id.signuppage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sname=(TextInputLayout)findViewById(R.id.signup_name);
        semail=(TextInputLayout)findViewById(R.id.signup_email);
        spassword=(TextInputLayout)findViewById(R.id.signup_password);
        sbtn=(Button) findViewById(R.id.signup_btn);

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sname.getEditText().getText().toString().isEmpty() && !semail.getEditText().getText().toString().isEmpty() && !spassword.getEditText().getText().toString().isEmpty()) {
                    String name = sname.getEditText().getText().toString();
                    String email = semail.getEditText().getText().toString();
                    String password = spassword.getEditText().getText().toString();
                    mprogress.setTitle("Registering New User");
                    mprogress.setMessage("Kindly wait");
                    mprogress.setCanceledOnTouchOutside(false);
                    mprogress.show();
                    if (name != "" && email != "" && password !="")
                        registerNewUser(name, email, password);
                }
                else {
                    //Toast.makeText(SignupActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    final Snackbar snack = Snackbar.make(signup_page, "Empty Credentials", 3000).setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(Color.GREEN);
                    snack.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_signup,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id)
        {
            case R.id.settings_option:Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();
                                        break;
            case R.id.users_option:Toast.makeText(this,"Users clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutus_option:Toast.makeText(this,"About Us clicked",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerNewUser(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser currUser=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=currUser.getUid();
                            mdatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String,String> usermap=new HashMap<>();
                            usermap.put("name",name);
                            usermap.put("image","default");
                            mdatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mprogress.dismiss();
                                        Log.d("Success", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent mainIntent=new Intent(SignupActivity.this,LoginActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                    else {
                                        mprogress.dismiss();
                                        currUser.delete();
                                        Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mprogress.hide();
                            Log.w("Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
