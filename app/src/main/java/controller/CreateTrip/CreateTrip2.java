package controller.CreateTrip;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nga.uit.edu.mytravel.R;

public class CreateTrip2 extends AppCompatActivity implements LocationListener {
    private LocationAdapter locationAdapter;
    private Button btAdd;
    private List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip_2);


        RecyclerView locationsRecyclerView = findViewById(R.id.LocationRecyclerView);
        locationsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        locationsRecyclerView.setLayoutManager(layoutManager);
        btAdd = findViewById(R.id.btAddToList);


        locationList = new ArrayList<>();
        locationList.add(new Location(R.drawable.ic_greengps, "Yên Bái", "Yen Bai, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vũng Tàu", "Vung Tau, Ba Ria - Vung Tau, Viet Nam,"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vĩnh Long", "Vinh Long, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Tuyên Quang", "Tuyen Quang, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vinh", "Vinh, Nghe An, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Nha Trang", "Nha Trang, Khanh Hoa,  Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Seven", "Sixteen"));
        locationList.add(new Location(R.drawable.ic_greengps, "Eight", "Seventeen"));
        locationList.add(new Location(R.drawable.ic_greengps, "Nine", "Eighteen"));

        locationAdapter = new LocationAdapter(locationList,this);
        locationsRecyclerView.setAdapter(locationAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Location> selectedLocations = locationAdapter.getSelectedLocation();

                StringBuilder locationNames = new StringBuilder();
                for(int i =0;i<selectedLocations.size();i++) {
                    if (i==0) {
                        locationNames.append(selectedLocations.get(i).strName);
                    } else {
                        locationNames.append("\n").append(selectedLocations.get(i).strName);
                    }
                }
                Toast.makeText(CreateTrip2.this, locationNames.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationAction(Boolean isselected) {
        if(isselected) {
            btAdd.setVisibility(View.VISIBLE);
        } else {
            btAdd.setVisibility(View.GONE);
        }
    }

    @Override
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
    }
}
