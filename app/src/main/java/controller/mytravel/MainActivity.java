package controller.mytravel;

import androidx.appcompat.app.AppCompatActivity;

import controller.CreateTrip.CreateTrip1;
import controller.CreateTrip.Trip6Activity;
import nga.uit.edu.mytravel.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void xuLyChamManHinh(View view) {

        Intent intent=new Intent(MainActivity.this, HomeScreen.class);

        startActivity(intent);
    }
}