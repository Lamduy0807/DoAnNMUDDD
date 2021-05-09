package controller.mytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import nga.uit.edu.mytravel.R;

public class TravelLocationAdapter extends RecyclerView.Adapter<TravelLocationAdapter.TravelLocationViewHolder> {

    private List<TravelLocation> travelLocations;

    public TravelLocationAdapter(List<TravelLocation> travelLocations) {
        this.travelLocations = travelLocations;
    }

    @NonNull
    @Override
    public TravelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelLocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_travellocation,parent,false
                )
        );

    }

    @Override
    public void onBindViewHolder(@NonNull TravelLocationViewHolder holder, int position) {
        holder.setLocationData(travelLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return travelLocations.size();
    }

    static class TravelLocationViewHolder extends RecyclerView.ViewHolder{
        private KenBurnsView kbvLocation;
        private TextView tvTitle, tvLocation, tvStarrate;
        public TravelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            tvLocation = itemView.findViewById(R.id.txtLocation);
            tvTitle = itemView.findViewById(R.id.txtTitle);
            tvStarrate = itemView.findViewById(R.id.txtStar);
        }
        void setLocationData (TravelLocation travelLocation)
        {
            Picasso.get().load(travelLocation.imgURL).into(kbvLocation);
            tvTitle.setText(travelLocation.title);
            tvLocation.setText(travelLocation.location);
            tvStarrate.setText(String.valueOf(travelLocation.starrating));
        }
    }
}
