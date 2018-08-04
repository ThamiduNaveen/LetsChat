package com.example.asus_pc.letschat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersActivity extends AppCompatActivity {

    private Toolbar usersToolbar;
    private RecyclerView allUserList;
    private DatabaseReference allUsersDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        usersToolbar = (Toolbar) findViewById(R.id.all_users_toolbar);
        setSupportActionBar(usersToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allUserList = (RecyclerView) findViewById(R.id.recyclerView_allUsers);
        allUserList.setHasFixedSize(true);
        allUserList.setLayoutManager(new LinearLayoutManager(this));

        allUsersDBRef = FirebaseDatabase.getInstance().getReference().child("Users");



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AllUsers,AllUsersViewHolder> fireBaseRecAdapter = new FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder>
                (AllUsers.class,R.layout.all_users_list_layout,AllUsersViewHolder.class, allUsersDBRef) {
            @Override
            protected void populateViewHolder(AllUsersViewHolder viewHolder, AllUsers model, int position) {
                viewHolder.setUser_name(model.getUser_name());
                viewHolder.setUser_status(model.getUser_status());
                viewHolder.setUser_image(model.getUser_image());
            }
        };
        allUserList.setAdapter(fireBaseRecAdapter);

    }

    public static class AllUsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public AllUsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setUser_name(String user_name) {
            TextView name = (TextView) mView.findViewById(R.id.TextView_userName);
            name.setText(user_name);
        }
        public void setUser_status(String user_status) {
            TextView status = (TextView) mView.findViewById(R.id.TextView_status);
            status.setText(user_status);
        }
        public void setUser_image(String user_image) {
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.all_users_profile_image);
            Picasso.get().load(user_image).into(image);
        }
    }
}
