package controller.mytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nga.uit.edu.mytravel.R;

public class TravelDiscoveryAdapter extends RecyclerView.Adapter<TravelDiscoveryAdapter.TravelDiscoveryViewHolder>{
    private List<TravelDiscovery> travelDiscoveryList;

    public TravelDiscoveryAdapter(List<TravelDiscovery> travelDiscoveryList) {
        this.travelDiscoveryList = travelDiscoveryList;
    }

    @NonNull
    @Override
    public TravelDiscoveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelDiscoveryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDiscoveryViewHolder holder, int position) {
            holder.setLocationData(travelDiscoveryList.get(position));
    }

    @Override
    public int getItemCount() {
        return travelDiscoveryList.size();
    }

    static class TravelDiscoveryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title, location;
        public TravelDiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgDiscovery);
            title = itemView.findViewById(R.id.txtTitleDiscovery);
            location = itemView.findViewById(R.id.txtLocationDiscovery);
        }
        void setLocationData(TravelDiscovery travelDiscovery)
        {
            imageView.setImageResource(travelDiscovery.URL);
            title.setText(travelDiscovery.title);
            location.setText(travelDiscovery.location);
        }
    }
}
