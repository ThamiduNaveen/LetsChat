package com.example.asus_pc.letschat;

import android.content.Intent;
import android.sax.StartElementListener;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private ViewPager mviewPager;
    private TabLayout mtabLayout;

    private TabsPagerAdapter mTabsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Let's chat");

        mviewPager = (ViewPager) findViewById(R.id.main_tab_pager);
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mTabsPagerAdapter);
        mtabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mtabLayout.setupWithViewPager(mviewPager);

    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            LogOutUser();
        }
    }

    private void LogOutUser() {
        Intent main_startPage = new Intent(MainActivity.this, StartPageActivity.class);
        main_startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //the above line block user pressing back
        startActivity(main_startPage);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.button_main_logout) {
            mAuth.signOut();
            LogOutUser();
        }
        if (item.getItemId()== R.id.button_main_settings){
            Intent main_settings = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(main_settings);
        }
        if (item.getItemId()== R.id.button_main_allUsers){
            Intent main_allUsers = new Intent(MainActivity.this,AllUsersActivity.class);
            startActivity(main_allUsers);
        }

        return true;
    }
}
