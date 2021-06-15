package controller.mytravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import controller.CreateTrip.CreateTrip1;
import controller.CreateTrip.CreateTrip2;
import controller.CreateTrip.DetailPlaceActivity;
import controller.CreateTrip.Location;
import controller.CreateTrip.Place;
import controller.CreateTrip.Trip6Activity;
import controller.minh.ProfileActivity;
import nga.uit.edu.mytravel.R;

public class HomeScreen extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private ViewPager2 viewPager3;
    private Handler slideHandler = new Handler();
    private BottomNavigationView bottomNavigationView ;
    private DatabaseReference databaseReference,data;

    private SearchView searchView;
    private ListView LocationList;
    private ArrayList<String> List;
    private ArrayAdapter<String> adapter;
    TravelLocationAdapter travelLocationAdapter;
    FirebaseAuth firebaseAuth;
    String title, location, imgURL;
    float starRate;
    ImgSlideAdapter imgSlideAdapter;
    FrameLayout f1, f2, f3;
    String titleimg;
    String titleDiscovery, urlimgDiscovery;
    String t1,t2,t3;
    String p1,p2,p3;
    TextView tv1, tv2, tv3;
    RoundedImageView im1,im2,im3;
    List<TravelLocation> travelLocationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        viewPager2 = findViewById(R.id.VP);
        viewPager3 = findViewById(R.id.VP2Img);
        bottomNavigationView = findViewById(R.id.bottom_navi);
        tv1 = findViewById(R.id.tvDiscovery1);
        im1 = findViewById(R.id.imgDiscovery1);
        tv2 = findViewById(R.id.tvDiscovery2);
        im2 = findViewById(R.id.imgDiscovery2);
        tv3 = findViewById(R.id.tvDiscovery3);
        im3 = findViewById(R.id.imgDiscovery3);
        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        firebaseAuth = FirebaseAuth.getInstance();
        SetBottomNavigationBar();
        SetImgSlide();
        SetDiscovery();
        SetViewPaperTravelocation();
        SetupSearchView();



    }

    private void SetDiscovery() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ImgDiscovery");
        databaseReference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleDiscovery = snapshot.child("title").getValue().toString()+ " - " +snapshot.child("place").getValue().toString();
                    tv1.setText(titleDiscovery);
                    Picasso.get().load(snapshot.child("url").getValue().toString()).into(im1);
                    p1 = snapshot.child("title").getValue().toString();
                    t1 = snapshot.child("place").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, DetailPlaceActivity.class);
                intent.putExtra("title", p1);
                intent.putExtra("strName",t1 );
                startActivity(intent);
            }
        });
        databaseReference.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleDiscovery = snapshot.child("title").getValue().toString()+ " - " +snapshot.child("place").getValue().toString();
                    tv2.setText(titleDiscovery);
                    Picasso.get().load(snapshot.child("url").getValue().toString()).into(im2);
                    p2 = snapshot.child("title").getValue().toString();
                    t2 = snapshot.child("place").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, DetailPlaceActivity.class);
                intent.putExtra("title", p2);
                intent.putExtra("strName",t2 );
                startActivity(intent);
            }
        });
        databaseReference.child("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleDiscovery =snapshot.child("title").getValue().toString()+ " - " +snapshot.child("place").getValue().toString();
                    tv3.setText(titleDiscovery);
                    Picasso.get().load(snapshot.child("url").getValue().toString()).into(im3);
                    p3 = snapshot.child("title").getValue().toString();
                    t3 = snapshot.child("place").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, DetailPlaceActivity.class);
                intent.putExtra("title", p3);
                intent.putExtra("strName",t3 );
                startActivity(intent);
            }
        });
    }


    private void SetupSearchView() {
        searchView = findViewById(R.id.searchview);
        LocationList = findViewById(R.id.locationList);
        List = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,List);
        LocationList.setAdapter(adapter);
        data = FirebaseDatabase.getInstance().getReference("Việt Nam");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Location location = dataSnapshot.getValue(Location.class);
                    List.add(location.getStrName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = LocationList.getItemAtPosition(position).toString();
                Toast.makeText(HomeScreen.this,""+select,Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(HomeScreen.this, Trip6Activity.class);
                intent.putExtra("strName",LocationList.getItemAtPosition(position).toString() );
                startActivity(intent);

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
//        imgSlideList.add(new ImgSlide(R.drawable.hcm));
//        imgSlideList.add(new ImgSlide(R.drawable.hn));
//        imgSlideList.add(new ImgSlide(R.drawable.dn));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ImgSlide");

        imgSlideAdapter = new ImgSlideAdapter(imgSlideList,viewPager3);
        databaseReference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleimg = snapshot.child("url").getValue().toString();


                    imgSlideList.add(new ImgSlide(titleimg));

                }
                imgSlideAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        databaseReference.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleimg = snapshot.child("url").getValue().toString();


                    imgSlideList.add(new ImgSlide(titleimg));

                }
                imgSlideAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        databaseReference.child("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    titleimg = snapshot.child("url").getValue().toString();


                    imgSlideList.add(new ImgSlide(titleimg));

                }
                imgSlideAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        viewPager3.setAdapter(imgSlideAdapter);

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


        databaseReference = FirebaseDatabase.getInstance().getReference().child("TravelLocation");

        travelLocationAdapter = new TravelLocationAdapter(this,travelLocationList);
        databaseReference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    title = snapshot.child("title").getValue().toString();
                    location = snapshot.child("location").getValue().toString();
                    imgURL = snapshot.child("imgURL").getValue().toString();
                    starRate = Float.parseFloat(snapshot.child("starRate").getValue().toString());

                    travelLocationList.add(new TravelLocation(title, location, imgURL, starRate));

                }
                travelLocationAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        databaseReference.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    title = snapshot.child("title").getValue().toString();
                    location = snapshot.child("location").getValue().toString();
                    imgURL = snapshot.child("imgURL").getValue().toString();
                    starRate = Float.parseFloat(snapshot.child("starRate").getValue().toString());

                    travelLocationList.add(new TravelLocation(title, location, imgURL, starRate));

                }
                travelLocationAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        databaseReference.child("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    title = snapshot.child("title").getValue().toString();
                    location = snapshot.child("location").getValue().toString();
                    imgURL = snapshot.child("imgURL").getValue().toString();
                    starRate = Float.parseFloat(snapshot.child("starRate").getValue().toString());

                    travelLocationList.add(new TravelLocation(title, location, imgURL, starRate));

                }
                    travelLocationAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        viewPager2.setAdapter(travelLocationAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
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