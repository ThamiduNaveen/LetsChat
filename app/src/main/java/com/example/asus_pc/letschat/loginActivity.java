package com.example.asus_pc.letschat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class loginActivity extends AppCompatActivity {

    private Toolbar loginToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle("SIGN IN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
