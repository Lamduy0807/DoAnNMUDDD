package controller.CreateTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nga.uit.edu.mytravel.R;

public class DetailPlaceActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private ImageView mainImageView;
    private TextView txtTitle;
    private TextView txtAddress;
    private TextView txtDescription;
    private DatabaseReference mData;
    private DatabaseReference database_DanhGia;

    private EditText edtDanhGia;
    private Button btnDangTai;
     String mpath="";
     String mtitle="";

    private RecyclerView recyclerView_DanhGia;
    private List<DanhGia> mDanhGia;
    private DanhGiaAdapter danhGiaAdapter;


    private Context context;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        email=firebaseUser.getEmail();

        addControls();
        addEvents();
        getData();
        mData =  FirebaseDatabase.getInstance().getReference().child("Place").child(mpath).child(mtitle);
        database_DanhGia = FirebaseDatabase.getInstance().getReference().child("Đánh Giá").child("Place").child(mpath).child(mtitle);
        loadData();
        mDanhGia.clear();
        loadDanhGia();


    }

    private void loadDanhGia() {
        database_DanhGia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhGia danhGia = dataSnapshot.getValue(DanhGia.class);
                    mDanhGia.add(danhGia);

                }
                danhGiaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addEvents() {
        btnDangTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDanhGia.getText().toString().trim().equals(""))
                {
                    Toast.makeText(DetailPlaceActivity.this,"Hãy chia sẻ trải nghiệm của ban",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DanhGia danhGia = new DanhGia();
                    danhGia.setUid(email);
                    danhGia.setTextDanhGia(edtDanhGia.getText().toString());
                    saveDanhGia(danhGia);
                    Toast.makeText(DetailPlaceActivity.this,"Đã đăng tải đánh giá của bạn",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void saveDanhGia(DanhGia danhGia) {

        database_DanhGia.child(database_DanhGia.push().getKey()).setValue(danhGia);


    }

    private void addControls() {
        mainImageView= this.<ImageView>findViewById(R.id.imgPlace);
        txtTitle= this.<TextView>findViewById(R.id.txtTitle);
        txtAddress= this.<TextView>findViewById(R.id.txtAddress);
        txtDescription = findViewById(R.id.txtDescription);

        edtDanhGia = findViewById(R.id.edtDanhGia);
        btnDangTai = findViewById(R.id.btnDangTai);

        recyclerView_DanhGia = findViewById(R.id.recyclerView_DanhGia);
        mDanhGia = new ArrayList<>();
        danhGiaAdapter = new DanhGiaAdapter(DetailPlaceActivity.this, mDanhGia);
        recyclerView_DanhGia.setAdapter(danhGiaAdapter);
        recyclerView_DanhGia.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_DanhGia.setHasFixedSize(true);



    }
    private void getData()
    {
        if(getIntent().hasExtra("title") )
        {
            Intent intent = getIntent();
            mtitle=intent.getStringExtra("title");
            mpath=intent.getStringExtra("strName");
        }
        else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }

    }

    private void loadData() {

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Place place = snapshot.getValue(Place.class);
                txtTitle.setText(place.getTitle());
                txtAddress.setText(place.getsAddress());
                txtDescription.setText(place.getDescription());
                Glide.with(DetailPlaceActivity.this).load(place.getLinkHinh()).into(mainImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}