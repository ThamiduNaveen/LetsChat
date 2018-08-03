package com.example.asus_pc.letschat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar statusToolbar;

    private Button saveBT;
    private EditText statusET;

    private DatabaseReference statusDatabaseRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        statusDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);


        statusToolbar = (Toolbar) findViewById(R.id.status_toolbar);
        setSupportActionBar(statusToolbar);
        getSupportActionBar().setTitle("Change status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBT = (Button)findViewById(R.id.button_status_save);
        statusET = (EditText) findViewById(R.id.editText_status_status);

        String old_status = getIntent().getExtras().get("current_status").toString();

        statusET.setText(old_status);

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newStatus = statusET.getText().toString();
                ChangeStatus(newStatus);
            }
        });

    }

    private void ChangeStatus(String newStatus) {
        if(TextUtils.isEmpty(newStatus)){
            Toast.makeText(this, "Please write your status", Toast.LENGTH_SHORT).show();
        }
        else{
            statusDatabaseRef.child("user_status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(StatusActivity.this, "Status updated", Toast.LENGTH_SHORT).show();
                        Intent status_settings = new Intent(StatusActivity.this,SettingsActivity.class);
                        startActivity(status_settings);
                    }
                    else{
                        Toast.makeText(StatusActivity.this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
