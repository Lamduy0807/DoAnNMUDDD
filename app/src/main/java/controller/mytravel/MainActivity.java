package controller.mytravel;

import androidx.appcompat.app.AppCompatActivity;

import controller.CreateTrip.CreateTrip1;
import controller.CreateTrip.Trip6Activity;
import nga.uit.edu.mytravel.R;

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

public class MainActivity extends AppCompatActivity {
    Animation topani;
    ImageView imgLogo;
    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        imgLogo = findViewById(R.id.logo);
        topani = AnimationUtils.loadAnimation(this,R.anim.top_ani);

        imgLogo.setAnimation(topani);

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