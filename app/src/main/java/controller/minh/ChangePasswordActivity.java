package controller.minh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nga.uit.edu.mytravel.R;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

    }

    public void Back(View view) {
        Intent intent= new Intent(ChangePasswordActivity.this, ChangeProfileActivity.class);
        startActivity(intent);    }
}