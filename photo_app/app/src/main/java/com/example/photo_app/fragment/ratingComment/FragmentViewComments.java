package com.example.photo_app.fragment.ratingComment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.ImageActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.ratingComment.CommentListAdapter;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.ratingComment.CommentService;
import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewComments extends Fragment {
    private static final String TAG = "ViewCommentsFragment";
    EditText eComment;
    private ListView listView;
    private ImageView mBackArrow, ivCheckMark;

    CommentListAdapter adapter;

    private List<Comment> mComments;

    private CommentService commentService;
    private String photoId;
    private Long userId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_comments, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commentService = ApiClient.createService(CommentService.class, getContext());
        photoId = getArguments().getString("photo_id");
        userId = getArguments().getLong("user_id");
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        eComment = view.findViewById(R.id.comment);
        ivCheckMark = (ImageView) view.findViewById(R.id.ivPostComment);
        listView = (ListView) view.findViewById(R.id.listView);
        mComments = new ArrayList<>();
//        adapter = new CommentListAdapter(this,
//                R.layout.layout_comment, mComments);
        adapter = new CommentListAdapter(getActivity(), mComments);
        adapter.setPhotoId(photoId);
        listView.setAdapter(adapter);

        ivCheckMark.setOnClickListener(v -> {

            if(!eComment.getText().toString().equals("")){
                Log.d(TAG, "onClick: attempting to submit new comment.");
                addNewComment(eComment.getText().toString());
                eComment.setText("");
                closeKeyboard();
            }else{
//                Toast.makeText(getActivity(), "you can't post a blank comment", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "you can't post a blank comment", Toast.LENGTH_SHORT).show();
            }
        });
        getAllComments();

        mBackArrow.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating back");
            getActivity().getSupportFragmentManager().popBackStack();
            ImageActivity context = (ImageActivity) getContext();
            context.showLayout();
        });
    }

    private void closeKeyboard(){
//        View view = getActivity().getCurrentFocus();
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //call api
    private void addNewComment(String message){
        Log.d(TAG, "addNewComment: adding new comment: " + message);

//        String commentID = myRef.push().getKey();

//        Comment comment = new Comment();
//        comment.setComment(newComment);
//        comment.setDate_created(getTimestamp());
//        comment.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        CommentDTO comment = new CommentDTO();
        comment.setMessage(message);
        comment.setPhotoId(photoId);
        commentService.create(comment,userId.toString()).enqueue(new Callback<MessageResponse<Comment>>() {

            @Override
            public void onResponse(Call<MessageResponse<Comment>> call, Response<MessageResponse<Comment>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Server Response: " + + response.code() + " " + response.message());
                    Toast.makeText(getContext(), "Comment posted", Toast.LENGTH_SHORT).show();
                    getAllComments();
                }else{
                    Log.d(TAG, "onResponse: Server Response: " + response.code() + " " + response.message());
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse<Comment>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "không gọi được đến server", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        //insert into photos node
//        myRef.child(getString(R.string.dbname_photos))
//                .child(mPhoto.getPhoto_id())
//                .child(getString(R.string.field_comments))
//                .child(commentID)
//                .setValue(comment);
//
//        //insert into user_photos node
//        myRef.child(getString(R.string.dbname_user_photos))
//                .child(mPhoto.getUser_id()) //should be mphoto.getUser_id()
//                .child(mPhoto.getPhoto_id())
//                .child(getString(R.string.field_comments))
//                .child(commentID)
//                .setValue(comment);

    }
    public void getAllComments() {
        Log.d(TAG, "getAllComments: getting a list of all comments");
        commentService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Comment>>>() {

            @Override
            public void onResponse(Call<MessageResponse<List<Comment>>> call, Response<MessageResponse<List<Comment>>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: get all comment success");
                    List<Comment> comments = response.body().getData();
                    comments.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                    Long clientId = response.body().getClient();
                    for(Comment comment : comments){
                        Log.d(TAG, "onResponse: comment: " + comment.getMessage());
                    }
                    adapter.setUserId(clientId);
                    adapter.updateComments(comments);
                    listView.invalidateViews();
                    adapter.notifyDataSetChanged();
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
    }

}
