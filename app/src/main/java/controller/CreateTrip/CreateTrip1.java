    package controller.CreateTrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nga.uit.edu.mytravel.R;

public class CreateTrip1 extends AppCompatActivity {
    Button btnChoose;
    TextView tvDestination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip_1);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnChoose = findViewById(R.id.btnChooseLocation);
        tvDestination = findViewById(R.id.tvDestination);
    }
    private void addEvents() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLocation();
            }
        });
    }


    private void ChooseLocation() {
        Intent intent=new Intent(CreateTrip1.this, CreateTrip2.class);
        startActivity(intent);
    }
}
