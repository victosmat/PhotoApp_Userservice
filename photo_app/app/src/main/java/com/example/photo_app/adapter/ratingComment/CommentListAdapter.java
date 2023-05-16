package com.example.photo_app.adapter.ratingComment;

import android.app.AlertDialog;
import android.content.Context;
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

import com.example.photo_app.R;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.ratingComment.CommentService;
import com.example.photo_app.fragment.ratingComment.FragmentViewComments;
import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends BaseAdapter {

    private static final String TAG = "CommentListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;
    private List<Comment> comments;
    private CommentService commentService;
    private Long userId = null;
    private String photoId = null;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    //    public CommentListAdapter(@NonNull Context context, @LayoutRes int resource,
//                              @NonNull List<Comment> objects) {
//        super(context, resource, objects);
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mContext = context;
//        layoutResource = resource;
//        comments = objects;
//    }
    public CommentListAdapter(@NonNull Context context, @NonNull List<Comment> objects) {
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        comments = objects;
        commentService = ApiClient.createService(CommentService.class, context);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private static class ViewHolder{
        TextView comment, username, timestamp, reply, likes;
        CircleImageView profileImage;
        ImageView edit, del;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

//        if(convertView == null){
//            convertView = mInflater.inflate(layoutResource, parent, false);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_comment, parent, false);
            holder = new ViewHolder();

            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.username = (TextView) convertView.findViewById(R.id.comment_username);
            holder.timestamp = (TextView) convertView.findViewById(R.id.comment_time_posted);
            holder.edit = (ImageView) convertView.findViewById(R.id.comment_edit);
            holder.del = (ImageView) convertView.findViewById(R.id.comment_del);
//            holder.likes = (TextView) convertView.findViewById(R.id.comment_likes);
//            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.comment_profile_image);

            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }

        //set the comment
//        holder.comment.setText(getItem(position).getComment());
        Comment comment = (Comment) getItem(position);
        holder.comment.setText(comment.getMessage());

        //set the timestamp difference
//        String timestampDifference = getTimestampDifference(getItem(position));
//        if(!timestampDifference.equals("0")){
//            holder.timestamp.setText(timestampDifference + " d");
//        }else{
//            holder.timestamp.setText("today");
//        }
        holder.timestamp.setText(comment.getLastUpdate()!=null ?comment.getLastUpdate().toString():"null day");

        //set the username and profile image
        holder.username.setText(comment.getUsername() != null? comment.getUsername() : "null user");
        holder.edit.setOnClickListener(view -> {
            long id = comment.getId();
            EditText editView = new EditText(view.getContext());
            editView.setText(comment.getMessage());
            AlertDialog.Builder editDialog = new AlertDialog.Builder(view.getContext());
            editDialog.setTitle("EDIT COMMENT");
            editDialog.setView(editView);
            editDialog.setPositiveButton("Edit", (dialog, which) -> {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(id);
                commentDTO.setMessage(editView.getText().toString());
                if(comment.getPhotoId() != null) commentDTO.setPhotoId(comment.getPhotoId());
                if(comment.getPostId() != null) commentDTO.setPostId(comment.getPostId());
                commentDTO.setUserId(comment.getUserId());
                commentService.update(commentDTO).enqueue(new Callback<MessageResponse<Comment>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Comment>> call, Response<MessageResponse<Comment>> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: success delete");
//                            comments.remove(position);
//                            notifyDataSetChanged();
                            Log.d(TAG, "getAllComments: getting a list of all comments");

                            commentService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Comment>>>() {

                                @Override
                                public void onResponse(Call<MessageResponse<List<Comment>>> call, Response<MessageResponse<List<Comment>>> response) {
                                    if(response.isSuccessful()){
                                        Log.d(TAG, "onResponse: get all comment success");
                                        List<Comment> comments = response.body().getData();
                                        comments.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                                        for(Comment comment : comments){
                                            Log.d(TAG, "onResponse: comment: " + comment.getMessage());
                                        }
                                        updateComments(comments);
                                        notifyDataSetChanged();
                                    }else{
                                        Log.d(TAG, "onResponse: get all comment fail");
                                        Log.d(TAG, response.code() + " " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponse<List<Comment>>> call, Throwable t) {
                                    Log.d(TAG, "onFailure: get all comment fail");
                                }
                            });
                        }else{
                            Log.d(TAG, "onResponse: response fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Comment>> call, Throwable t) {

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
            long id = comment.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("WARNING");
            builder.setMessage("Are you sure you want to delete this comment ?");
//            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Yes", (dialog, which) -> {
//                int[] ids = {id};
                commentService.deleteById(String.valueOf(id)).enqueue(new Callback<MessageResponse<String>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<String>> call, Response<MessageResponse<String>> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: success delete");
//                            comments.remove(position);
//                            notifyDataSetChanged();
                            Log.d(TAG, "getAllComments: getting a list of all comments");
                            commentService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Comment>>>() {

                                @Override
                                public void onResponse(Call<MessageResponse<List<Comment>>> call, Response<MessageResponse<List<Comment>>> response) {
                                    if(response.isSuccessful()){
                                        Log.d(TAG, "onResponse: get all comment success");
                                        List<Comment> comments = response.body().getData();
                                        comments.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                                        for(Comment comment : comments){
                                            Log.d(TAG, "onResponse: comment: " + comment.getMessage());
                                        }
                                        updateComments(comments);
                                        notifyDataSetChanged();
                                    }else{
                                        Log.d(TAG, "onResponse: get all comment fail");
                                        Log.d(TAG, response.code() + " " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponse<List<Comment>>> call, Throwable t) {
                                    Log.d(TAG, "onFailure: get all comment fail");
                                }
                            });
                        }else{
                            Log.d(TAG, "onResponse: fail");
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
            if(comment.getUserId() != userId){
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

    public void updateComments(List<Comment> comments){
        Log.d(TAG, "updateComments: updating comment list.");
        this.comments.clear();
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }
}