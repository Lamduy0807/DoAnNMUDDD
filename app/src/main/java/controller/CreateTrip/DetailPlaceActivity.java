    package controller.CreateTrip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private FirebaseFirestore firestore;

    private EditText edtDanhGia;
    private Button btnDangTai;
     String mpath="";
     String mtitle="";

    private RecyclerView recyclerView_DanhGia;
    private List<DanhGia> mDanhGia;
    private DanhGiaAdapter danhGiaAdapter;

    String uid, email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        uid=firebaseUser.getUid();
        email = firebaseUser.getEmail();

        addControls();
        addEvents();
        getData();
        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        firestore = FirebaseFirestore.getInstance();
        mData =  FirebaseDatabase.getInstance().getReference().child("Place").child(mpath).child(mtitle);
        database_DanhGia = FirebaseDatabase.getInstance().getReference().child("Đánh Giá").child("Place").child(mpath).child(mtitle);
        loadData();
        showDanhGia();


    }

    private void showDanhGia() {
        firestore.collection(mtitle).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        String id = documentChange.getDocument().getId();
                        DanhGia danhGia = documentChange.getDocument().toObject(DanhGia.class);

                       mDanhGia.add(danhGia);
                       danhGiaAdapter.notifyDataSetChanged();

                    }
                }
                Collections.reverse(mDanhGia);

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

                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("uid",uid);
                    taskMap.put("email", email);
                    taskMap.put("textDanhGia",edtDanhGia.getText().toString());
                    saveDataDanhGia(taskMap);

                }
            }
        });

    }

    private void saveDataDanhGia(Map taskMap) {
        firestore.collection(mtitle).add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(DetailPlaceActivity.this,"Đã đăng tải đánh giá của bạn",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(DetailPlaceActivity.this,"Đăng tải đánh giá không thành công",Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailPlaceActivity.this,e.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView_DanhGia.addItemDecoration(itemDecoration);



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