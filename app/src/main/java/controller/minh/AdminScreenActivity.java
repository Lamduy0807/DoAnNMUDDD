package controller.minh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import controller.CreateTrip.CreateTrip2;
import controller.mytravel.HomeScreen;
import controller.mytravel.LoginScreen;
import controller.mytravel.MainActivity;
import controller.mytravel.User;
import nga.uit.edu.mytravel.R;

public class AdminScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView cardView_Place;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.nav_admin);
        toolbar = findViewById(R.id.toolbar_admin);
        cardView_Place = findViewById(R.id.cardview_Place_admin);
        cardView_Place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreenActivity.this, CreateTrip2.class);
                startActivity(intent);

            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_admin);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home_admin:
                return true;
            case R.id.nav_place_admin:
                Intent intent = new Intent(AdminScreenActivity.this, CreateTrip2.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            case  R.id.nav_managerAcc_admin:
                Toast.makeText(AdminScreenActivity.this,"Manager Account Activity",Toast.LENGTH_SHORT).show();
                return true;
            case  R.id.nav_profile_admin:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0,0);
                return true;
            case  R.id.nav_logout_admin:
                mAuth.signOut();
                signOutUser();
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOutUser() {
        Intent intent = new Intent(AdminScreenActivity.this, LoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}