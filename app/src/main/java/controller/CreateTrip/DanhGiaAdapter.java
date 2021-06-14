package controller.CreateTrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nga.uit.edu.mytravel.R;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.DanhGiaViewHolder> {
    private Context context;
    private List<DanhGia> danhGiaList;

    public DanhGiaAdapter(Context context, List<DanhGia> danhGiaList) {
        this.context = context;
        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public DanhGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_danhgia,parent,false);
        return new DanhGiaAdapter.DanhGiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaViewHolder holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);
        holder.txtUid.setText(danhGia.getUid());
        holder.txtDanhGia.setText(danhGia.getTextDanhGia());

    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public class DanhGiaViewHolder extends RecyclerView.ViewHolder {
        TextView txtDanhGia;
        TextView txtUid;

        public DanhGiaViewHolder(@NonNull View itemview)
        {
            super(itemview);
            txtDanhGia = itemview.findViewById(R.id.txtDanhGia);
            txtUid = itemview.findViewById(R.id.txtUid);

        }

    }
}
