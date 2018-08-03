package com.example.asus_pc.letschat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private TextView userNameTW,statusTW;
    private Button changeImageBT,changeStatusBT;
    private CircleImageView profileImage;

    private DatabaseReference getUserDataRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userNameTW = (TextView) findViewById(R.id.textView_settings_userName);
        statusTW = (TextView) findViewById(R.id.textView_settings_status);
        changeImageBT = (Button) findViewById(R.id.button_settings_changeImage);
        changeStatusBT = (Button) findViewById(R.id.button_settings_changeStatus);
        profileImage = (CircleImageView) findViewById(R.id.profile_image_settings);

        mAuth = FirebaseAuth.getInstance();
        String onlineUser = mAuth.getCurrentUser().getUid();
        getUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(onlineUser);
        getUserDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("user_name").getValue().toString();
                String status = dataSnapshot.child("user_status").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();

                userNameTW.setText(name);
                statusTW.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        

    }
}
