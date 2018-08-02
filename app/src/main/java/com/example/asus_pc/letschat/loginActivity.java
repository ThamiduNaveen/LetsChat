package com.example.asus_pc.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class  loginActivity extends AppCompatActivity {

    private Toolbar loginToolbar;

    private Button loginBT;

    private EditText emailET;
    private EditText passwardET;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle("SIGN IN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginBT = (Button) findViewById(R.id.button_login_signIn);
        emailET = (EditText) findViewById(R.id.editText_login_email);
        passwardET = (EditText) findViewById(R.id.editText_login_passward);

        loadingBar = new ProgressDialog(this);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String passward = passwardET.getText().toString();
                LoginUserAccount(email,passward);
            }
        });

    }

    private void LoginUserAccount(String email, String passward) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your Email address", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(passward)){
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wail for verifying...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,passward)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent login_main = new Intent(loginActivity.this,MainActivity.class);
                        login_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login_main);
                        finish();
                    }
                    else{
                        Toast.makeText(loginActivity.this, "Login Fail, check your email and password", Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }
    }
}
