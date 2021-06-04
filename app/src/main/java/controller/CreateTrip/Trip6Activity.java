package controller.CreateTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import nga.uit.edu.mytravel.R;

public class Trip6Activity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap map;

    private RecyclerView recyclerView;
    String strPlace[], strAddress[];
    int images[]={R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu,R.drawable.van_thanh_mieu};
    int imgGps=R.drawable.ic_greengps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip6);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        addControls();

    }


    private void addControls() {


        strPlace=getResources().getStringArray(R.array.list_place);
        strAddress=getResources().getStringArray(R.array.address_place);
        recyclerView= this.<RecyclerView>findViewById(R.id.recyclerView);
        PlaceAdapter placeAdapter=new PlaceAdapter(this,strPlace, strAddress,images);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        //LatLng HaNoii = new LatLng(21.60140226455056, 105.65808202184448);

        // CHỖ NÀY CẦN LẤY TỪ CƠ SỞ DỮ LIỆU
        // CẦN CÓ TÊN PLACE, TITLE CÓ DẤU CHO PLACE
        // CẦN TỌA ĐỘ
        //KHI BẤM VÀO MỖI ĐỊA CHỈ SẼ LOAD HẾT CÁC MAKER LÊN MAP
        LatLng VanThanhMieu = new LatLng(10.243557795769036, 105.98465411534355);
        map.addMarker(new MarkerOptions().position(VanThanhMieu).title("Văn Thánh Miếu"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(VanThanhMieu,13));

        LatLng DinhLongThanh = new LatLng(10.239409866144607, 105.98989378465645);
        map.addMarker(new MarkerOptions().position(DinhLongThanh).title("Đình Long Thanh"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DinhLongThanh,13));

        LatLng VinhLong = new LatLng(10.239381668635907, 105.96243661914919);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(VinhLong, 13));




    }
}