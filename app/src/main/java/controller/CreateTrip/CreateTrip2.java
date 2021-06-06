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
        btAdd.setVisibility(View.VISIBLE);

        locationList = new ArrayList<>();
        locationList.add(new Location(R.drawable.ic_greengps, "Yên Bái", "Yen Bai, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vũng Tàu", "Vung Tau, Ba Ria - Vung Tau, Viet Nam,"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vĩnh Long", "Vinh Long, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Tuyên Quang", "Tuyen Quang, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Vinh", "Vinh, Nghe An, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Nha Trang", "Nha Trang, Khanh Hoa,  Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Sài Gòn", "Sai Gon, Ho Chi Minh, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Hà Nội", "Ha Noi, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "An Giang", "An Giang, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Bạc Liêu", "Bac Lieu, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Bắc Giang", "Bac Giang, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Cần Thơ", "Can Tho, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Đắk Lắk", "Dak Lak, Viet Nam"));
        locationList.add(new Location(R.drawable.ic_greengps, "Đồng Nai", "Dong Nai, Viet Nam"));


        locationAdapter = new LocationAdapter(locationList,this);
        locationsRecyclerView.setAdapter(locationAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationAdapter.getSelected() !=null) {
                    Toast.makeText(CreateTrip2.this,locationAdapter.getSelected().getStrName(),Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(CreateTrip2.this, Trip6Activity.class);
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
