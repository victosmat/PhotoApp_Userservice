package com.example.photo_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.photo_app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;

// adapter for showing list of images
public class ImageListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> filePaths;

    public ImageListAdapter(Context context, ArrayList<String> filePaths) {
        this.context = context;
        this.filePaths = filePaths;
    }

    @Override
    public int getCount() {
        return filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_img_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgView);
        imageView.setImageURI(Uri.parse(filePaths.get(position)));

        return convertView;
    }

}
