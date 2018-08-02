package com.example.asus_pc.letschat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class registerActivity extends AppCompatActivity {

    private Toolbar registerToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle("SIGN UP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
