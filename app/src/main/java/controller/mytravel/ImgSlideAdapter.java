package controller.mytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import nga.uit.edu.mytravel.R;

public class ImgSlideAdapter extends RecyclerView.Adapter<ImgSlideAdapter.ImgSlideViewHoder>{
    private List<ImgSlide> imgSlideList;
    private ViewPager2 viewPager2;

    public ImgSlideAdapter(List<ImgSlide> imgSlideList, ViewPager2 viewPager2) {
        this.imgSlideList = imgSlideList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ImgSlideViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImgSlideViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImgSlideViewHoder holder, int position) {
        holder.setImage(imgSlideList.get(position));
        if(position==imgSlideList.size()-2)
        {
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return imgSlideList.size();
    }

    class ImgSlideViewHoder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public ImgSlideViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgslide);
        }
        void setImage(ImgSlide imgSlide)
        {
            Picasso.get().load(imgSlide.getUrl()).into(imageView);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imgSlideList.addAll(imgSlideList);
            notifyDataSetChanged();
        }
    };
}
