package controller.mytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

import controller.CreateTrip.CreateTrip1;
import controller.minh.ProfileActivity;
import nga.uit.edu.mytravel.R;

public class HomeScreen extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private ViewPager2 viewPager3;
    private Handler slideHandler = new Handler();
    private BottomNavigationView bottomNavigationView ;

    private SearchView searchView;
    private ListView LocationList;
    private ArrayList<String> List;
    private ArrayAdapter<String> adapter;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        viewPager2 = findViewById(R.id.VP);
        viewPager3 = findViewById(R.id.VP2Img);
        bottomNavigationView = findViewById(R.id.bottom_navi);

        firebaseAuth = FirebaseAuth.getInstance();
        SetBottomNavigationBar();
        SetViewPaperTravelocation();
        SetImgSlide();
        SetupSearchView();



    }


    private void SetupSearchView() {
        searchView = findViewById(R.id.searchview);
        LocationList = findViewById(R.id.locationList);
        List = new ArrayList<>();
        List.add("Yen Bai");
        List.add("Vung Tau");
        List.add("Vinh Long");
        List.add("Tuyen Quang");
        List.add("Vinh");
        List.add("Nha Trang");
        List.add("Sai Gon");
        List.add("Ha Noi");
        List.add("An Giang");
        List.add("Bac Lieu");
        List.add("Bac Giang");
        List.add("Dak Lak");
        List.add("Dong Nai");
        List.add("Can Tho");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,List);
        LocationList.setAdapter(adapter);
        LocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = LocationList.getItemAtPosition(position).toString();
                Toast.makeText(HomeScreen.this,""+select,Toast.LENGTH_SHORT).show();

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                LocationList.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LocationList.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                if (newText.isEmpty())
                    LocationList.setVisibility(View.GONE);
                return false;
            }
        });

    }


    private void SetBottomNavigationBar() {
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_home:
                    {
                        return true;
                    }
                    case R.id.action_trip: {
                        startActivity(new Intent(getApplicationContext(), CreateTrip1.class));
                        overridePendingTransition(0, 0);
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

    private void SetImgSlide() {
        List<ImgSlide> imgSlideList = new ArrayList<>();
        imgSlideList.add(new ImgSlide(R.drawable.hcm));
        imgSlideList.add(new ImgSlide(R.drawable.hn));
        imgSlideList.add(new ImgSlide(R.drawable.dn));

        viewPager3.setAdapter(new ImgSlideAdapter(imgSlideList,viewPager3));

        viewPager3.setClipToPadding(false);
        viewPager3.setClipChildren(false);
        viewPager3.setOffscreenPageLimit(3);
        viewPager3.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager3.setPageTransformer(compositePageTransformer);

        viewPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderrunnable);
                slideHandler.postDelayed(sliderrunnable,3000);
            }
        });



    }
    private Runnable sliderrunnable = new Runnable()
    {
        @Override
        public void run() {
            viewPager3.setCurrentItem(viewPager3.getCurrentItem() + 1);
        }
    };

    private void SetViewPaperTravelocation() {
        List<TravelLocation> travelLocationList = new ArrayList<>();
        travelLocationList.add(new TravelLocation("Beautiful Beach","Vũng Tàu",R.drawable.vt,4.7f));
        travelLocationList.add(new TravelLocation("Beautiful Mountain","Sapa",R.drawable.sp,4.6f));
        travelLocationList.add(new TravelLocation("Beautiful Plateau","Đà Lạt",R.drawable.dl,4.9f));

        viewPager2.setAdapter(new TravelLocationAdapter(travelLocationList));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }


    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity( new Intent(HomeScreen.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onStart() {
        checkUserStatus();
        super.onStart();
    }

}