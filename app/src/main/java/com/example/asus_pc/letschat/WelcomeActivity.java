package com.example.asus_pc.letschat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Thread thread =new Thread(){
            @Override
            public void run() {
                try {
                    sleep(6000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent welcome_main = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(welcome_main);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
