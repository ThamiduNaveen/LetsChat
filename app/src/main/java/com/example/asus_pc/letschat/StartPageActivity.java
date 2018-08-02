package com.example.asus_pc.letschat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    private Button newAccountBT;
    private Button haveAccountBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        newAccountBT = (Button) findViewById(R.id.button_startPage_newAccount);
        haveAccountBT = (Button) findViewById(R.id.button_startPage_haveAccount);

        newAccountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_register = new Intent(StartPageActivity.this,registerActivity.class);
                startActivity(start_register);
            }
        });

        haveAccountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_login = new Intent(StartPageActivity.this,loginActivity.class);
                startActivity(start_login);
            }
        });


    }
}
