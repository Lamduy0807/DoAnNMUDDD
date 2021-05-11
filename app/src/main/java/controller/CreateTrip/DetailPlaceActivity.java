package controller.CreateTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nga.uit.edu.mytravel.R;

public class DetailPlaceActivity extends AppCompatActivity {

    ImageView mainImageView;
    TextView txtTitle;
    TextView txtInfo;
    TextView txtAddress;

    String data1, data2;
    int images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);

        addControls();

    }

    private void addControls() {
        mainImageView= this.<ImageView>findViewById(R.id.mainImageView);
        txtTitle= this.<TextView>findViewById(R.id.txtTitle);
        txtAddress= this.<TextView>findViewById(R.id.txtAddress);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("images") && getIntent().hasExtra("data1") &&
        getIntent().hasExtra("data2"))
        {

            data1=getIntent().getStringExtra("data1");
            data2=getIntent().getStringExtra("data2");
            images=getIntent().getIntExtra("images",1);
        }
        else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        txtTitle.setText(data1);
        txtAddress.setText(data2);
        mainImageView.setImageResource(images);
    }
}