package controller.minh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import controller.mytravel.LoginActivity;
import controller.mytravel.LoginScreen;
import nga.uit.edu.mytravel.R;

public class ProfileActivity extends AppCompatActivity {
  private  CircleImageView profilePic;
  private ImageView changePic;
  private FirebaseAuth mAuth;
  private DatabaseReference databaseReference;
  private Uri imageUri;
  private String MyUri="";
  private StorageTask uploadTask;
  private StorageReference storageReference;
  private TextView txtEmail,txtID;
  private Button btnLogout;
  private ImageView ivUpdatePass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile);

       mAuth = FirebaseAuth.getInstance();
       databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
       storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pic");
      btnLogout=findViewById(R.id.btnLogout);
       profilePic=findViewById(R.id.img);
       changePic=findViewById(R.id.btnChangePic);
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
               Intent openGallerryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
               startActivityForResult(openGallerryIntent,1000);
              uploadProfileImage();
           }
       });
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
        if(requestCode==1000){
            if(resultCode== RESULT_OK && data!= null){
                imageUri = data.getData();
                profilePic.setImageURI(imageUri);
            }
            else{
                Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
            }
        }

    }

   
}