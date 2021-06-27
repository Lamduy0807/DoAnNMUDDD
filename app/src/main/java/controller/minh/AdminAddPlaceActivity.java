package controller.minh;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import controller.CreateTrip.Place;
import controller.CreateTrip.Trip6Activity;
import nga.uit.edu.mytravel.R;

public class AdminAddPlaceActivity extends AppCompatActivity {
    TextView tv_select_province;
    EditText edt_title;
    EditText edt_address;
    EditText edt_description;
    EditText edt_latitude;
    EditText edt_longitude;
    Button btnSavePlace, btnCancelPlace;
    ImageView imgPlace;

    int REQUET_CODE_IMAGE = 1;
    private DatabaseReference mData;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_place);

        addControls();
        addEvents();
        getData();

        mData = FirebaseDatabase.getInstance().getReference().child("Place").child(tv_select_province.getText().toString()).child(edt_title.getText().toString());
        storageReference = FirebaseStorage.getInstance().getReference();

        loadData();
    }

    private void getData() {
        if(getIntent().hasExtra("name_province") )
        {
            Intent intent = getIntent();
            tv_select_province.setText(intent.getStringExtra("name_province"));

        }
        else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
       mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void addEvents() {

        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUET_CODE_IMAGE);
            }
        });



        btnSavePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(edt_title.getText().equals("") || edt_address.getText().equals("") || edt_description.toString().equals("")
                || edt_latitude.getText().equals("")||edt_longitude.getText().equals("") ) {
                    Toast.makeText(AdminAddPlaceActivity.this, "Pleas enter info", Toast.LENGTH_SHORT).show();
                }
                else {*/
                    if (imageUri != null) {
                        saveToFirebase(imageUri);

                    } else {

                        Toast.makeText(AdminAddPlaceActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    }
                //}

                /*if(edt_title.getText()!=null && edt_address.getText() !=null
                        && edt_description.getText() !=null && edt_latitude.getText() !=null
                        && edt_longitude.getText() !=null    ) {

                    if (imageUri != null) {
                        saveToFirebase(imageUri);

                    } else {

                        Toast.makeText(AdminAddPlaceActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(AdminAddPlaceActivity.this, "Please enter full infomation", Toast.LENGTH_SHORT).show();

                }*/

        }});


        ////CẦN LÀM DIALOG HỎI Ý KIẾN
        btnCancelPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddPlaceActivity.this, Trip6Activity.class);
                intent.putExtra("strName",tv_select_province.getText().toString());
                startActivity(intent);
                finish();

            }
        });
    }


    private void saveToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("Place").child(tv_select_province.getText().toString()).child(edt_title.getText().toString());
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Place place = new Place();
                        place.setTitle(edt_title.getText().toString());
                        place.setsAddress(edt_address.getText().toString());
                        place.setDescription(edt_description.getText().toString());
                        place.setViDo(Double.parseDouble(edt_latitude.getText().toString()));
                        place.setKinhDo(Double.parseDouble(edt_longitude.getText().toString()));
                        place.setLinkHinh(uri.toString());



                        mData.child(edt_title.getText().toString()).setValue(place);
                        Toast.makeText(AdminAddPlaceActivity.this, "Add New Place Seccesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminAddPlaceActivity.this, Trip6Activity.class);
                        intent.putExtra("strName",tv_select_province.getText().toString());
                        startActivity(intent);
                        finish();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAddPlaceActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();


            }
        });

    }
   /* private String getFileExtension(Uri mUri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mine  = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(mUri));
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUET_CODE_IMAGE && resultCode == RESULT_OK && data!= null)
        {
            imageUri=data.getData();
            imgPlace.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void addControls() {
        tv_select_province = findViewById(R.id.tv_select_province);

        edt_title = findViewById(R.id.edt_enter_title);
        edt_address = findViewById(R.id.edt_enter_address);
        edt_description = findViewById(R.id.edt_enter_description);
        edt_latitude = findViewById(R.id.edt_enter_latitude);
        edt_longitude = findViewById(R.id.edt_enter_longitude);
        btnSavePlace = findViewById(R.id.btnSavePlace);
        btnCancelPlace = findViewById(R.id.btnCancelPlace);
        imgPlace = findViewById(R.id.imgPlace);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminAddPlaceActivity.this, Trip6Activity.class);
        intent.putExtra("strName",tv_select_province.getText().toString());
        startActivity(intent);
        finish();

    }
}