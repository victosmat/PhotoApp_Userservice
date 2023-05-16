package com.example.photo_app.adapter.ratingComment;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.photo_app.R;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.ratingComment.RatingService;
import com.example.photo_app.model.ratingComment.Rating;
import com.example.photo_app.model.ratingComment.RatingDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingListAdapter extends BaseAdapter {

    private static final String TAG = "RatingListAdapter";

    private LayoutInflater mInflater;
    private Button btnRate;
    private Context mContext;
    private List<Rating> ratings;
    private RatingService ratingService;
    private Long userId = null;
    private String photoId = null;

    public RatingListAdapter(@NonNull Context context, @NonNull List<Rating> objects, Button btnRate) {
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        ratings = objects;
        ratingService = ApiClient.createService(RatingService.class, context);
        this.btnRate = btnRate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    private static class ViewHolder{
        TextView comment, username, timestamp, reply, likes;
        RatingBar ratingBar;
        CircleImageView profileImage;
        ImageView edit, del;
    }

    @Override
    public int getCount() {
        return ratings.size();
    }

    @Override
    public Object getItem(int i) {
        return ratings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final RatingListAdapter.ViewHolder holder;

//        if(convertView == null){
//            convertView = mInflater.inflate(layoutResource, parent, false);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_rating, parent, false);
        holder = new RatingListAdapter.ViewHolder();

        holder.comment = (TextView) convertView.findViewById(R.id.comment);
        holder.username = (TextView) convertView.findViewById(R.id.comment_username);
        holder.timestamp = (TextView) convertView.findViewById(R.id.comment_time_posted);
        holder.edit = (ImageView) convertView.findViewById(R.id.comment_edit);
        holder.del = (ImageView) convertView.findViewById(R.id.comment_del);
        holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating);
//            holder.likes = (TextView) convertView.findViewById(R.id.comment_likes);
//            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.comment_profile_image);

        convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }

        Rating rating = (Rating) getItem(position);
        holder.comment.setText(rating.getMessage());
        holder.ratingBar.setRating(rating.getRating());
        Log.i(TAG, "getView: " + rating.getRating());
        //set the timestamp difference
//        String timestampDifference = getTimestampDifference(getItem(position));
//        if(!timestampDifference.equals("0")){
//            holder.timestamp.setText(timestampDifference + " d");
//        }else{
//            holder.timestamp.setText("today");
//        }
        holder.timestamp.setText(rating.getLastUpdate()!=null ?rating.getLastUpdate().toString():"null day");

        //set the username and profile image
        holder.username.setText(rating.getUsername() != null? rating.getUsername() : "null user");
        holder.edit.setOnClickListener(view -> {
            long id = rating.getId();
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            EditText editView = new EditText(view.getContext());
            editView.setText(rating.getMessage());
            RatingBar ratingBar = new RatingBar(view.getContext());
            ratingBar.setNumStars(5);
            ratingBar.setRating(rating.getRating());
            ratingBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(editView);
            linearLayout.addView(ratingBar);
            AlertDialog.Builder editDialog = new AlertDialog.Builder(view.getContext());
            editDialog.setTitle("EDIT RATING");
            editDialog.setView(linearLayout);
            editDialog.setPositiveButton("Edit", (dialog, which) -> {
                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setId(id);
                ratingDTO.setMessage(editView.getText().toString());
                ratingDTO.setRating((int) ratingBar.getRating());
                if(rating.getPhotoId() != null) ratingDTO.setPhotoId(rating.getPhotoId());
                ratingDTO.setUserId(rating.getUserId());
                ratingService.update(ratingDTO).enqueue(new Callback<MessageResponse<Rating>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Rating>> call, Response<MessageResponse<Rating>> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: success delete");
//                            notifyDataSetChanged();
                            Log.d(TAG, "getAllRatings: getting a list of all ratings");
                            ratingService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Rating>>>() {

                                @Override
                                public void onResponse(Call<MessageResponse<List<Rating>>> call, Response<MessageResponse<List<Rating>>> response) {
                                    if(response.isSuccessful()){
                                        Log.d(TAG, "onResponse: get all ratings success");
                                        List<Rating> ratings = response.body().getData();
                                        ratings.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                                        updateRatings(ratings);
                                        notifyDataSetChanged();
                                        for(Rating rating : ratings){
                                            Log.d(TAG, "onResponse: rating: " + rating.getMessage());
                                        }
                                    }else{
                                        Log.d(TAG, "onResponse: get all ratings fail");
                                        Log.d(TAG, response.code() + " " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponse<List<Rating>>> call, Throwable t) {
                                    Log.d(TAG, "onFailure: get all ratings fail");
                                }
                            });
                        }else{
                            Log.d(TAG, "onResponse: response fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Rating>> call, Throwable t) {

                    }
                });
//                finish();
            });
            editDialog.setNegativeButton("No", (dialog, which) -> {

            });
            AlertDialog dialog = editDialog.create();
            dialog.show();
        });
        holder.del.setOnClickListener(view -> {
            long id = rating.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("WARNING");
            builder.setMessage("Are you sure you want to delete this rating ?");
//            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Yes", (dialog, which) -> {
//                int[] ids = {id};
                ratingService.deleteById(String.valueOf(id)).enqueue(new Callback<MessageResponse<String>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<String>> call, Response<MessageResponse<String>> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: success delete");
//                            notifyDataSetChanged();
                            Log.d(TAG, "getAllRatings: getting a list of all ratings");
                            ratingService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Rating>>>() {

                                @Override
                                public void onResponse(Call<MessageResponse<List<Rating>>> call, Response<MessageResponse<List<Rating>>> response) {
                                    if(response.isSuccessful()){
                                        Log.d(TAG, "onResponse: get all ratings success");
                                        List<Rating> ratings = response.body().getData();
                                        ratings.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                                        updateRatings(ratings);
                                        notifyDataSetChanged();
                                        for(Rating rating : ratings){
                                            Log.d(TAG, "onResponse: rating: " + rating.getMessage());
                                            if(rating.getUserId()==userId){
                                                btnRate.setEnabled(false);
                                                btnRate.setClickable(false);
                                                return;
                                            }
                                        }
                                        btnRate.setEnabled(true);
                                        btnRate.setClickable(true);
                                    }else{
                                        Log.d(TAG, "onResponse: get all ratings fail");
                                        Log.d(TAG, response.code() + " " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponse<List<Rating>>> call, Throwable t) {
                                    Log.d(TAG, "onFailure: get all rating fail");
                                }
                            });
                        }else{
                            Log.d(TAG, "onResponse: fail");
                            Log.d(TAG, response.code()+ " "+ response.message());

                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<String>> call, Throwable t) {

                    }
                });
//                finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> {

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        try{
            if(rating.getUserId() != userId){
                holder.edit.setVisibility(View.GONE);
                holder.del.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            Log.e(TAG, "getView: NullPointerException: " + e.getMessage() );
            holder.edit.setVisibility(View.GONE);
            holder.del.setVisibility(View.GONE);
        }


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(mContext.getString(R.string.dbname_user_account_settings))
//                .orderByChild(mContext.getString(R.string.field_user_id))
//                .equalTo(getItem(position).getUser_id());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
//                    holder.username.setText(
//                            singleSnapshot.getValue(UserAccountSettings.class).getUsername());
//
//                    ImageLoader imageLoader = ImageLoader.getInstance();
//
//                    imageLoader.displayImage(
//                            singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
//                            holder.profileImage);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: query cancelled.");
//            }
//        });
//
//        try{
//            if(position == 0){
//                holder.like.setVisibility(View.GONE);
//                holder.likes.setVisibility(View.GONE);
//                holder.reply.setVisibility(View.GONE);
//            }
//        }catch (NullPointerException e){
//            Log.e(TAG, "getView: NullPointerException: " + e.getMessage() );
//        }


        return convertView;
    }

    public void updateRatings(List<Rating> ratings){
        Log.d(TAG, "updateRatings: updating rating list.");
        this.ratings.clear();
        this.ratings.addAll(ratings);
        notifyDataSetChanged();
    }
}
