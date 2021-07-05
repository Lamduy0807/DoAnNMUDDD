package controller.Home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;
import controller.Admin.AdminScreenActivity;
import nga.uit.edu.mytravel.R;

public class MainActivity extends AppCompatActivity {
    Animation topani;
    ImageView imgLogo;
    private static int SPLASH_SCREEN = 3000;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    private String strUID;
    private  String permission = "";
    public static Boolean IsChangeAvatar = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        imgLogo = findViewById(R.id.logo);
        topani = AnimationUtils.loadAnimation(this,R.anim.top_ani);
        imgLogo.setAnimation(topani);

    }

    @Override
    public void onStart() {
        checkUserStatus();
        super.onStart();
    }
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if(!user.isEmailVerified())
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                        Pair[] pairs = new Pair[1];
                        pairs[0] = new Pair<View,String>(imgLogo,"logo_img");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                            startActivity(intent,options.toBundle());
                        }
                    }
                },2000);

            }
            else {
                checkUserPermission(user);
            }

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View,String>(imgLogo,"logo_img");

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                }
            },SPLASH_SCREEN);

        }

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
                if(IsChangeAvatar==false) {
                    if (permission.equals("admin")) {
                        startActivity(new Intent(MainActivity.this, AdminScreenActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, HomeScreen.class));
                    }

                }
                else if(IsChangeAvatar==true) {
                    IsChangeAvatar=false;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}