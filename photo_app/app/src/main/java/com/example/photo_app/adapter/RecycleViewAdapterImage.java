package com.example.photo_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photo_app.R;
import com.example.photo_app.fragment.FragmentProfile;
import com.example.photo_app.model.call.flickr.PhotosByUserResponse;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterImage extends RecyclerView.Adapter<RecycleViewAdapterImage.HomeViewHolder> {
    private List<PhotosByUserResponse.photoByUserResponse> urlImages;
    private RecycleViewAdapterImage.ItemListener itemListener;

    public RecycleViewAdapterImage(Context context, List<PhotosByUserResponse.photoByUserResponse>[] list) {
    }

    public RecycleViewAdapterImage(FragmentActivity activity, FragmentProfile fragmentProfile) {
    }

    public void setItemListener(RecycleViewAdapterImage.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapterImage(PhotosByUserResponse.photoByUserResponse viewType) {
        urlImages = new ArrayList<>();
    }

    public void setList(List<PhotosByUserResponse.photoByUserResponse> urlImages) {
        this.urlImages = urlImages;
        notifyDataSetChanged();
    }

    public PhotosByUserResponse.photoByUserResponse getItem(int p) {
        return urlImages.get(p);
    }

    @NonNull
    @Override
    public RecycleViewAdapterImage.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new RecycleViewAdapterImage.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        PhotosByUserResponse.photoByUserResponse urlImage = urlImages.get(position);
//        holder.ivImage.setImageResource(R.drawable.ic_android);
        Glide.with(holder.itemView)
                .load(urlImage.getUrl())
                .placeholder(R.drawable.ic_android)
                .error(R.drawable.ic_android)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return urlImages.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivImage;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.OnItemClick(v, getAdapterPosition());
            }

        }
    }


    public interface ItemListener {
        void OnItemClick(View view, int p);
    }
}
