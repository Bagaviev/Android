package com.example.rentateamtask.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentateamtask.R;
import com.example.rentateamtask.model.pojo.UserData;
import com.example.rentateamtask.view.fragments.UserListFragment;
import com.squareup.picasso.Picasso;

public class UserPage extends AppCompatActivity {
    UserData user;
    TextView textViewFN;
    TextView textViewLN;
    TextView textViewEM;
    TextView textViewID;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        textViewFN = findViewById(R.id.textViewFN);
        textViewLN = findViewById(R.id.textViewLN);
        textViewEM = findViewById(R.id.textViewEM);
        textViewID = findViewById(R.id.textViewID);
        avatar = findViewById(R.id.avatar);
        user = (UserData) getIntent().getSerializableExtra(UserListFragment.EXTRA_MESSAGE);
    }

    @Override
    protected void onStart() {
        textViewFN.setText(user.getFirstName());
        textViewLN.setText(user.getLastName());
        textViewEM.append(" " + user.getEmail());
        textViewID.append(" " + user.getId());
        Picasso.with(this)
                .load(user.getAvatar())
                .placeholder(R.drawable.default_user)
                .into(avatar);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        avatar = null;
        user = null;
        super.onDestroy();
    }
}
