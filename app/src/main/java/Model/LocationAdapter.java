    package Model;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import nga.uit.edu.mytravel.R;

    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable{

    private Context context;

    private List<Location> locations;
    private List<Location> locationListFull;

    private int checkedPosition = 0; // = -1: no selection


    public LocationAdapter(Context context, List<Location> locations) {
        this.context = context;
        this.locations = locations;
        this.locationListFull = locations;
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

    //CODE FILTER NGA FIX
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String strSearch = constraint.toString();
                    if(strSearch.isEmpty())
                    {
                        locations = locationListFull;
                    }
                    else
                    {
                        List<Location> filteredList = new ArrayList<>();
                        for (Location item : locationListFull) {
                            if (item.getStrName().toLowerCase().contains(strSearch.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        locations=filteredList;
                    }
                    FilterResults results = new FilterResults();
                    results.values = locations;

                    return results;

                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    locations = (List<Location>) results.values;
                    notifyDataSetChanged();

                }
            };
        }

//----------------------------------------------------------------------
        //CODE CỦA ĐẠI LÚC TRƯỚC

   /* @Override
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
    };*/

    class LocationViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layoutLocation;
        View viewbackground;
        TextView textName;
        TextView textDetail;
        ImageView imageSelected;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutLocation = itemView.findViewById(R.id.layoutLocation);

            viewbackground = itemView.findViewById(R.id.viewBackground);
            textName = itemView.findViewById(R.id.textName);
            textDetail = itemView.findViewById(R.id.textDetail);
            imageSelected = itemView.findViewById(R.id.imageSelected);
        }

        void bindLocation(final Location location) {
            textName.setText(location.strName);
            textDetail.setText(location.strDetail);
            if (checkedPosition == -1) {
                viewbackground.setBackgroundResource(R.drawable.location_background);

                imageSelected.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getBindingAdapterPosition()) {
                    viewbackground.setBackgroundResource(R.drawable.location_selected_background);
                    imageSelected.setVisibility(View.VISIBLE);
                } else {
                    viewbackground.setBackgroundResource(R.drawable.location_background);
                    imageSelected.setVisibility(View.GONE);
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewbackground.setBackgroundResource(R.drawable.location_selected_background);
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
