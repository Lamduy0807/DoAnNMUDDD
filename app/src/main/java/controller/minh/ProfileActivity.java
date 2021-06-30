package controller.minh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import controller.CreateTrip.CreateTrip1;
import controller.mytravel.HomeScreen;
import controller.mytravel.LoginScreen;
import controller.mytravel.User;
import nga.uit.edu.mytravel.R;

public class ProfileActivity extends AppCompatActivity {
  private  CircleImageView profilePic;
  private ImageView changePic;
  private FirebaseAuth mAuth;
  private DatabaseReference databaseReference;
  public static Uri imageUri;
  private String MyUri="";
  private StorageTask uploadTask;
  private StorageReference storageReference;
  private TextView txtEmail,txtID;
  private Button btnLogout;
  private ImageView ivUpdatePass,ivSave;
  private BottomNavigationView bottomNavigationView ;

  //
  private DatabaseReference mData;
    private String strUID;
    private  String permission = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile);

       mAuth = FirebaseAuth.getInstance();
       FirebaseUser user = mAuth.getCurrentUser();
       checkUserPermission(user);

       databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
       storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pic");
      btnLogout=findViewById(R.id.btnLogout);
      bottomNavigationView = findViewById(R.id.bottom_navipro);
      SetBottomNavigationBar();
       profilePic=findViewById(R.id.img);
       changePic=findViewById(R.id.btnChangePic);
//       ivSave=findViewById(R.id.ivSave);
       txtEmail=findViewById(R.id.txtmail);
       txtEmail.setText(mAuth.getCurrentUser().getEmail());
       txtID=findViewById(R.id.txtID);
       txtID.setText("UID: "+mAuth.getCurrentUser().getUid());
       ivUpdatePass=findViewById(R.id.ivUpdatePass);
       ivUpdatePass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(ProfileActivity.this, ChangePasswordActivity.class);
               startActivity(intent);
           }
       });
       btnLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
               signOutUser();
           }
       });
       changePic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               Intent openGallerryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//               startActivityForResult(openGallerryIntent,1000);
               CropImage.activity().setAspectRatio(1,1).start(ProfileActivity.this);

           }
       });
//       ivSave.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               uploadProfileImage();
//           }
//       });

       getUserInfo();


    }

    private void signOutUser() {
        Intent intent = new Intent(ProfileActivity.this,LoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()&& snapshot.getChildrenCount()>0){
                    if(snapshot.hasChild("image")){
                        String image =snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void uploadProfileImage() {
       final ProgressDialog db = new ProgressDialog(this);
       db.setTitle("Uploading");
       db.setMessage("Please wait...");
       db.show();
       if(imageUri != null){
           final StorageReference fileRef = storageReference
                   .child(mAuth.getCurrentUser().getUid()+".jpg");
           uploadTask = fileRef.putFile(imageUri);
           uploadTask.continueWithTask(new Continuation() {
               @Override
               public Object then(@NonNull @NotNull Task task) throws Exception {
                   if(!task.isSuccessful()){
                       throw task.getException();
                   }
                   return fileRef.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<Uri> task) {
                   if(task.isSuccessful()){
                       Uri downloadUrl = task.getResult();
                       MyUri = downloadUrl.toString();
                       HashMap<String,Object> userMap = new HashMap<>();
                       userMap.put("image",MyUri);
                       databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                       db.dismiss();
                   }
               }
           });
       }
       else{
           db.dismiss();
           Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1000){
//            if(resultCode== RESULT_OK && data!= null){
//                imageUri = data.getData();
//                profilePic.setImageURI(imageUri);
//                Toast.makeText(this, "Update Sucess, Please click Save button.", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//                alertDialog.setTitle("Save change?");
//                alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        uploadProfileImage();
//                    }
//                });
//                alertDialog.show();
//            }
//
//        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode== RESULT_OK && data!= null){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                profilePic.setImageURI(imageUri);
                Toast.makeText(this, "Update Sucess, Please click Save button.", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Save change?");
                alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadProfileImage();
                    }
                });
                alertDialog.show();
            }
            else{
                Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
        }
        }



    private void SetBottomNavigationBar() {
        bottomNavigationView.setSelectedItemId(R.id.action_pro);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_home:
                    {
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    case R.id.action_trip: {
                        startActivity(new Intent(getApplicationContext(), CreateTrip1.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    case R.id.action_pro:
                    {
                        return true;
                    }
                }
                return false;
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
                   // startActivity(new Intent(HomeScreen.this, AdminScreenActivity.class));
                    bottomNavigationView.setVisibility(View.INVISIBLE);

                }
                else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    //startActivity(new Intent(getContext(), HomeScreen.class));
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}