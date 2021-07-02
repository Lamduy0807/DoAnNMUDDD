package controller.CreateTrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import controller.Profile.ProfileActivity;
import controller.Home.HomeScreen;
import nga.uit.edu.mytravel.R;

public class CreateTrip1 extends AppCompatActivity {
    Button btnChoose;
    TextView tvDestination;
    private BottomNavigationView bottomNavigationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip_1);
        bottomNavigationView = findViewById(R.id.bottom_navi);
        SetBottomNavigationBar();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnChoose = findViewById(R.id.btnChooseLocation);
        tvDestination = findViewById(R.id.tvDestination);
    }
    private void addEvents() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLocation();
            }
        });
    }


    private void ChooseLocation() {
        Intent intent=new Intent(CreateTrip1.this, CreateTrip2.class);
        startActivity(intent);
    }
    private void SetBottomNavigationBar() {
        bottomNavigationView.setSelectedItemId(R.id.action_trip);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_home:
                    {
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.action_trip:
                    {

                        return true;
                    }
                    case R.id.action_pro:
                    {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
