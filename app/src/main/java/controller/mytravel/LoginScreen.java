package controller.mytravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nga.uit.edu.mytravel.R;

public class LoginScreen extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.VP);
        this.addTabs(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void addTabs(ViewPager viewPager)
    {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.AddFragment(new LoginFragment(),"Login");
        adapter.AddFragment(new RegisterFragment(),"Register");
        viewPager.setAdapter(adapter);
    }


}