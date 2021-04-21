package controller.minh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nga.uit.edu.mytravel.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile);
    }

    public void updateProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this,ChangeProfileActivity.class);
        startActivity(intent);
    }
}