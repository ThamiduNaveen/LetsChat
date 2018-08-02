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

public class registerActivity extends AppCompatActivity {

    private Toolbar registerToolbar;

    private EditText nameET;
    private EditText emailET;
    private EditText passwardET;

    private Button creatBT;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        registerToolbar = (Toolbar) findViewById(R.id.register_toolbar);

        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle("SIGN UP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = (EditText) findViewById(R.id.editText_register_name);
        emailET = (EditText) findViewById(R.id.editText_register_email);
        passwardET = (EditText) findViewById(R.id.editText_register_passward);

        creatBT = (Button) findViewById(R.id.button_register_create);

        creatBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String passward = passwardET.getText().toString();

                RegisterAccount(name, email, passward);

            }
        });


    }

    private void RegisterAccount(String name, String email, String passward) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter a name for Your account", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your Email address", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(passward)) {
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Creating new Account");
            loadingBar.setMessage("Please wait wile account is creating");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, passward)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent register_main = new Intent(registerActivity.this, MainActivity.class);
                                register_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(register_main);
                                finish();
                            } else {
                                Toast.makeText(registerActivity.this, "Error Occurred, Try Again ", Toast.LENGTH_LONG).show();
                            }
                            loadingBar.dismiss();
                        }

                    });
        }
    }

}
