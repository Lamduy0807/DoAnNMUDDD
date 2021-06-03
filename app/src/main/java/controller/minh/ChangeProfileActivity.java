package controller.minh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import controller.mytravel.LoginActivity;
import nga.uit.edu.mytravel.R;

public class ChangeProfileActivity extends AppCompatActivity {
    CircleImageView profileimg;
    Uri ImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        profileimg = findViewById(R.id.img);
    }

    public void changePass(View view) {
        Intent intent= new Intent(ChangeProfileActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void Close(View view) {
        Intent intent1= new Intent(ChangeProfileActivity.this, ProfileActivity.class);
        startActivity(intent1);
    }

    public void openGallery(View view) {
        Intent openGallerryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(openGallerryIntent,1000);
    }
   protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                ImageUri = data.getData();
                profileimg.setImageURI(ImageUri);
            }
        }
   }
}
