package com.example.asus_pc.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private TextView userNameTW, statusTW;
    private Button changeImageBT, changeStatusBT;
    private CircleImageView profileImage;

    private DatabaseReference getUserDataRef;
    private FirebaseAuth mAuth;

    private StorageReference profileImageStoreRef;


    private final static int gallery_pick = 1;

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
        getUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUser);
        profileImageStoreRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");

        getUserDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("user_name").getValue().toString();
                String status = dataSnapshot.child("user_status").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();

                Picasso.get().load(image).into(profileImage);
                

                userNameTW.setText(name);
                statusTW.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        changeImageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getGalleryImage = new Intent();
                getGalleryImage.setAction(Intent.ACTION_GET_CONTENT);
                getGalleryImage.setType("image/*");
                startActivityForResult(getGalleryImage, gallery_pick);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallery_pick && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                String userId = mAuth.getCurrentUser().getUid();
                StorageReference filepath = profileImageStoreRef.child(userId + ".jpg");


                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SettingsActivity.this, "Saving image..", Toast.LENGTH_SHORT).show();

                            String downloadUrlImage = task.getResult().getDownloadUrl().toString();
                            getUserDataRef.child("user_image").setValue(downloadUrlImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SettingsActivity.this, "Image Uploaded successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}
