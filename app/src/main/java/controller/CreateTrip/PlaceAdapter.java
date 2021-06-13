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

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import controller.mytravel.LoginActivity;
import nga.uit.edu.mytravel.R;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> implements Serializable {

    private Context context;
    private List<Place> placeList;

    public PlaceAdapter(Context context, List<Place> placeList) {
        this.context = context;
        this.placeList = placeList;
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

        Place mPlace = placeList.get(position);

        holder.txtTitle.setText(mPlace.getTitle());
        holder.txtAddressPlace.setText(mPlace.getsAddress());
        Glide.with(context).load(mPlace.getLinkHinh()).into(holder.imgPlace);


       holder.layoutPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailPlaceActivity.class);
                intent.putExtra("title", mPlace.getTitle());
                intent.putExtra("strName",Trip6Activity.path );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return placeList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layoutPlace;
        TextView txtTitle;
        TextView txtAddressPlace;
        ImageView imgPlace;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPlace=itemView.findViewById(R.id.layoutPlace);
            txtTitle=itemView.findViewById(R.id.txtTitlePlace);
            txtAddressPlace=itemView.findViewById(R.id.txtAddressPlace);
            imgPlace=itemView.findViewById(R.id.myImageView);

        }

    }
}

