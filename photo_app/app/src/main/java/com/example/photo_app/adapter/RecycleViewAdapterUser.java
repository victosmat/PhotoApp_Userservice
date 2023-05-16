package com.example.photo_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photo_app.R;
import com.example.photo_app.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterUser extends RecyclerView.Adapter<RecycleViewAdapterUser.HomeViewHolder> {
    private List<User> list;
    private ItemListener itemListener;

    public RecycleViewAdapterUser() {
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapterUser(String viewType) {
        list = new ArrayList<>();
    }

    public void setList(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public User getItem(int p) {
        return list.get(p);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        User item = list.get(position);
        holder.tvName.setText(item.getFullName());
        holder.tvAddress.setText(item.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvAddress;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
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
