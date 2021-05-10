package controller.CreateTrip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import controller.mytravel.LoginActivity;
import nga.uit.edu.mytravel.R;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
    String data1[], data2[];
    int images[];
    Context context;

    public PlaceAdapter(Context ct, String strPlace[], String strAddress[], int img[])
    {
        context=ct;
        data1=strPlace;
        data2=strAddress;
        images=img;

    }
    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_place,parent,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        holder.txtPlace.setText(data1[position]);
        holder.txtAddressPlace.setText(data2[position]);
        holder.myImageView.setImageResource(images[position]);
        holder.imgGPS.setImageResource(R.drawable.ic_greengps);

        holder.layoutPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailPlaceActivity.class);
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("images",images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return data1.length;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layoutPlace;
        TextView txtPlace, txtAddressPlace;
        ImageView myImageView, imgGPS;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPlace=itemView.findViewById(R.id.layoutPlace);
            txtPlace=itemView.findViewById(R.id.txtPlace);
            txtAddressPlace=itemView.findViewById(R.id.txtAddressPlace);
            myImageView=itemView.findViewById(R.id.myImageView);
            imgGPS=itemView.findViewById(R.id.imgGPS);

        }

    }
}

