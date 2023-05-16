package com.example.photo_app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.photo_app.ImageActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.ratingComment.CommentListAdapter;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.api.ratingComment.CommentService;
import com.example.photo_app.model.call.flickr.PhotoURLResponse;
import com.example.photo_app.model.notification.NotificationModel;
import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends BaseAdapter {

    private static final String TAG = "CommentListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;
    private List<NotificationModel> notificationModels;

    public NotificationAdapter(@NonNull Context context, @NonNull List<NotificationModel> objects) {
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        notificationModels = objects;
    }


    private static class ViewHolder{
        TextView noti_mess;
        ImageView detail;
        CircleImageView profileImage;
    }

    @Override
    public int getCount() {
        return notificationModels.size();
    }

    @Override
    public Object getItem(int i) {
        return notificationModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final NotificationAdapter.ViewHolder holder;

//        if(convertView == null){
//            convertView = mInflater.inflate(layoutResource, parent, false);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_noti, parent, false);
        holder = new NotificationAdapter.ViewHolder();

        holder.noti_mess = (TextView) convertView.findViewById(R.id.noti_mess);
        holder.detail = (ImageView) convertView.findViewById(R.id.detail);

        convertView.setTag(holder);

        //set the comment
//        holder.comment.setText(getItem(position).getComment());
        NotificationModel noti = (NotificationModel) getItem(position);
        holder.noti_mess.setText(noti.getContent());
//        if(noti.getStatus())
        holder.detail.setOnClickListener(view -> {
            FlickrService flickrService = GoClient.createServiceNonCookie(FlickrService.class, mContext);
            Call<PhotoURLResponse> photoCall = flickrService.getImageUrlByImgId(noti.getPhotoId());
            Log.i(TAG, "getView: "+noti.getPhotoId());
            photoCall.enqueue(new Callback<PhotoURLResponse>() {
                @Override
                public void onResponse(Call<PhotoURLResponse> call, Response<PhotoURLResponse> response) {
                    if (response.isSuccessful()) {
                        PhotoURLResponse photoURLResponse = response.body();
                        String photoUrl = photoURLResponse.getUrl();

                        Intent intent = new Intent(view.getContext(), ImageActivity.class);
                        intent.putExtra("image", photoURLResponse);
                        view.getContext().startActivity(intent);
                    }
                    else {
                        Log.e(TAG, "onResponse: " + response.code()+" "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<PhotoURLResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });



        return convertView;
    }

}
