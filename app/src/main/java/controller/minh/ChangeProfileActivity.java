package controller.minh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import controller.CreateTrip.CreateTrip1;
import controller.mytravel.HomeScreen;
import nga.uit.edu.mytravel.R;

public class ChangeProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        bottomNavigationView = findViewById(R.id.bottom_navi);
        SetBottomNavigationBar();
    }
    private void SetBottomNavigationBar() {
        bottomNavigationView.setSelectedItemId(R.id.action_pro);
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
                        startActivity(new Intent(getApplicationContext(), CreateTrip1.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.action_noti:
                    {

                    }
                    case R.id.action_pro:
                    {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}