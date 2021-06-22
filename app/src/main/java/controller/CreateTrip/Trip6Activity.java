package controller.CreateTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import controller.minh.AdminAddPlaceActivity;
import controller.minh.AdminScreenActivity;
import controller.mytravel.HomeScreen;
import controller.mytravel.User;
import nga.uit.edu.mytravel.R;

public class Trip6Activity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap map;
    private RecyclerView recyclerView;
    private List<Place> mList;
    private PlaceAdapter placeAdapter;
    private SearchView searchView;
    private DatabaseReference mRef;
    public static String path="";

    ///admin
    FloatingActionButton btnAddPlace;
    String strUID="";
    private DatabaseReference mData;
    public  String permission = "";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip6);

        Intent intent = getIntent();
        path=intent.getStringExtra("strName");

        mRef = FirebaseDatabase.getInstance().getReference().child("Place").child(path);

        addControls();

        loadData();
        searchPlace();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //admin
        btnAddPlace = findViewById(R.id.btnAddPlace);
        FirebaseUser user = mAuth.getCurrentUser();
        checkUserPermission(user);
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip6Activity.this, AdminAddPlaceActivity.class);
                intent.putExtra("name_province",path);
                startActivity(intent);
                finish();
            }
        });




    }
    private void checkUserPermission(FirebaseUser user)
    {
        mData = FirebaseDatabase.getInstance().getReference("Users");

        strUID = user.getUid();
        mData.child(strUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User mUser = snapshot.getValue(User.class);
                if(mUser!=null) {
                    permission = mUser.getPermission();
                }


                if(permission.equals("admin"))
                {
                    btnAddPlace.setVisibility(View.VISIBLE);

                }
                else {
                    btnAddPlace.setVisibility(View.INVISIBLE);
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadData() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Place place = dataSnapshot.getValue(Place.class);
                    mList.add(place);

                }
                placeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void addControls() {

        recyclerView= this.<RecyclerView>findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchPlace);

        mList =new ArrayList<>();
        placeAdapter=new PlaceAdapter(Trip6Activity.this,mList);

        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    private void searchPlace() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                placeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {


                    int i = 0;
                    List<LatLng> loc = new ArrayList<>();
                    for (Place pplace : mList) {
                        loc.add(new LatLng(pplace.getViDo(), pplace.getKinhDo()));
                    }

                    for (LatLng latLng : loc) {
                        map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(mList.get(i).getTitle()));
                        i++;

                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(loc.get(0)); //điểm A
                    builder.include(loc.get(loc.size() - 1)); //điểm B
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                    map.moveCamera(cu);
                    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);



            }
        });


    }


}