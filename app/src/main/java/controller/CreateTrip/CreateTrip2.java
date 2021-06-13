package controller.CreateTrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nga.uit.edu.mytravel.R;

public class CreateTrip2 extends AppCompatActivity implements LocationListener {
    private LocationAdapter locationAdapter;
    private Button btAdd;
    private List<Location> locationList;

    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip_2);

        mData = FirebaseDatabase.getInstance().getReference("Việt Nam");

        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView locationsRecyclerView = findViewById(R.id.LocationRecyclerView);
        locationsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        locationsRecyclerView.setLayoutManager(layoutManager);
        btAdd = findViewById(R.id.btAddToList);
        btAdd.setVisibility(View.VISIBLE);

        locationList = new ArrayList<>();
        loadData();


        locationAdapter = new LocationAdapter(CreateTrip2.this,locationList);
        locationsRecyclerView.setAdapter(locationAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationAdapter.getSelected() !=null) {
                    Toast.makeText(CreateTrip2.this,locationAdapter.getSelected().getStrName(),Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(CreateTrip2.this, Trip6Activity.class);
                    intent.putExtra("strName",locationAdapter.getSelected().getStrName() );
                    startActivity(intent);

                } else {
                    Toast.makeText(CreateTrip2.this,"No Selection",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onLocationAction(Boolean isselected) {
            btAdd.setVisibility(View.VISIBLE);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_location, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }*/

    private void loadData() {

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Location location = dataSnapshot.getValue(Location.class);
                    locationList.add(location);

                }
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
