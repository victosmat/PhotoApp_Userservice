package com.example.photo_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photo_app.R;
import com.example.photo_app.fragment.FragmentProfile;
import com.example.photo_app.model.call.flickr.PhotosByUserResponse;
import com.example.photo_app.model.call.flickr.PhotosetsResponse;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterAlbum extends RecyclerView.Adapter<RecycleViewAdapterAlbum.HomeViewHolder> {
    private List<PhotosetsResponse.PhotosetResponse> urlImages;
    private RecycleViewAdapterAlbum.ItemListener itemListener;

    public RecycleViewAdapterAlbum(Context context, List<PhotosByUserResponse.photoByUserResponse>[] list) {
    }

    public RecycleViewAdapterAlbum(FragmentActivity activity, FragmentProfile fragmentProfile) {
    }

    public RecycleViewAdapterAlbum() {

    }

    public void setItemListener(RecycleViewAdapterAlbum.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapterAlbum(PhotosetsResponse.PhotosetResponse viewType) {
        urlImages = new ArrayList<>();
    }

    public void setList(List<PhotosetsResponse.PhotosetResponse> urlImages) {
        this.urlImages = urlImages;
        notifyDataSetChanged();
    }

    public PhotosetsResponse.PhotosetResponse getItem(int p) {
        return urlImages.get(p);
    }

    @NonNull
    @Override
    public RecycleViewAdapterAlbum.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new RecycleViewAdapterAlbum.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterAlbum.HomeViewHolder holder, int position) {
        PhotosetsResponse.PhotosetResponse urlImage = urlImages.get(position);
//        holder.ivImage.setImageResource(R.drawable.ic_android);
        holder.txtTitleAlbum.setText(urlImage.getTitle());
        Glide.with(holder.itemView)
                .load(urlImage.getPrimary())
                .placeholder(R.drawable.ic_android)
                .error(R.drawable.ic_android)
                .into(holder.img_primary_album);
    }

    @Override
    public int getItemCount() {
        return urlImages.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img_primary_album;
        private TextView txtTitleAlbum;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_primary_album = itemView.findViewById(R.id.img_primary_album);
            txtTitleAlbum = itemView.findViewById(R.id.txtTitleAlbum);
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
