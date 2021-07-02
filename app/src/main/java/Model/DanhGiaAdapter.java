package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import controller.Profile.CircleImageView;
import nga.uit.edu.mytravel.R;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.DanhGiaViewHolder> {
    private Context context;
    private List<DanhGia> danhGiaList;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
   private String image;
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

        holder.txtUid.setText(danhGia.getEmail());
        holder.txtDanhGia.setText(danhGia.getTextDanhGia());
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        getUserInfo(holder, danhGia);

    }
    private void getUserInfo(@NonNull DanhGiaViewHolder holder, DanhGia danhGia) {
        databaseReference.child(danhGia.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()&& snapshot.getChildrenCount()>0){
                    if(snapshot.hasChild("image")){
                         image =snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(holder.avt);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public class DanhGiaViewHolder extends RecyclerView.ViewHolder {
        TextView txtDanhGia;
        TextView txtUid;
        CircleImageView avt;

        public DanhGiaViewHolder(@NonNull View itemview)
        {
            super(itemview);
            txtDanhGia = itemview.findViewById(R.id.txtDanhGia);
            txtUid = itemview.findViewById(R.id.txtUid);
            avt = itemview.findViewById(R.id.circleImageView);

        }

    }
}
