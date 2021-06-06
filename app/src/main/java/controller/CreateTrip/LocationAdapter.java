    package controller.CreateTrip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import nga.uit.edu.mytravel.R;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {

    private List<Location> locations;
    private List<Location> locationListFull;
    private LocationListener locationListener;
    private int checkedPosition = 0; // = -1: no selection

    public LocationAdapter(List<Location> locations, LocationListener locationListener) {
        this.locations = locations;
        this.locationListener = locationListener;
        locationListFull = new ArrayList<>(locations);

    }




    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.bindLocation(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public List<Location> getSelectedLocation(){
        List<Location> selectedLocation = new ArrayList<>();
        for (Location location : locations) {
            if(location.isSelected) {
                selectedLocation.add(location);
            }
        }
        return selectedLocation;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Location> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(locationListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Location item : locationListFull) {
                    if (item.getStrDetail().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            locations.clear();
            locations.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class LocationViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layoutLocation;
        View viewbackground;
        RoundedImageView imageLocation;
        TextView textName, textDetail;
        ImageView imageSelected;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutLocation = itemView.findViewById(R.id.layoutLocation);
            viewbackground = itemView.findViewById(R.id.viewBackground);
            imageLocation = itemView.findViewById(R.id.imageLocation);
            textName = itemView.findViewById(R.id.textName);
            textDetail = itemView.findViewById(R.id.textDetail);
            imageSelected = itemView.findViewById(R.id.imageSelected);
        }

        void bindLocation(final Location location) {
            imageLocation.setImageResource(location.image);
            textName.setText(location.strName);
            textDetail.setText(location.strDetail);
            if (checkedPosition == -1) {
                viewbackground.setBackgroundResource(R.drawable.location_background);
                imageLocation.setBackgroundResource(R.drawable.location_background);

                imageSelected.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getBindingAdapterPosition()) {
                    viewbackground.setBackgroundResource(R.drawable.location_selected_background);
                    imageLocation.setBackgroundResource(R.drawable.location_selected_background);
                    imageSelected.setVisibility(View.VISIBLE);
                } else {
                    viewbackground.setBackgroundResource(R.drawable.location_background);
                    imageLocation.setBackgroundResource(R.drawable.location_background);

                    imageSelected.setVisibility(View.GONE);
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewbackground.setBackgroundResource(R.drawable.location_selected_background);
                    imageLocation.setBackgroundResource(R.drawable.location_selected_background);
                    imageSelected.setVisibility(View.VISIBLE);
                    if (checkedPosition != getBindingAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getBindingAdapterPosition();
                    }
                }
            });
        }

    }

    public Location getSelected(){
        if (checkedPosition != -1) {
            return locations.get(checkedPosition);
        }
        return null;
    }
}
