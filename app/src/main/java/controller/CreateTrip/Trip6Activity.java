package controller.CreateTrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

import nga.uit.edu.mytravel.R;

public class Trip6Activity extends AppCompatActivity  {

    GoogleMap map;
    RecyclerView recyclerView;
    String strPlace[], strAddress[];
    int images[]={R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu};
    int imgGps=R.drawable.ic_greengps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip6);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        strPlace=getResources().getStringArray(R.array.list_place);
        strAddress=getResources().getStringArray(R.array.address_place);
        recyclerView= this.<RecyclerView>findViewById(R.id.recyclerView);
        PlaceAdapter placeAdapter=new PlaceAdapter(this,strPlace, strAddress,images);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}