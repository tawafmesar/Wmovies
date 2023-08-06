package com.example.wmovies.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wmovies.R;
import com.example.wmovies.models.Slide;

import java.util.List;

public class SliderPagerAdapter extends RecyclerView.Adapter<SliderPagerAdapter.SliderViewHolder> {

    private Context mContext;
    private List<Slide> mList;

    public SliderPagerAdapter(Context mContext, List<Slide> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView slideImg;
        TextView slideText;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImg = itemView.findViewById(R.id.slide_img);
            slideText = itemView.findViewById(R.id.slide_title);
        }

        public void bindView(int position) {
            slideImg.setImageResource(mList.get(position).getImage());
            slideText.setText(mList.get(position).getTitle());
        }
    }
}