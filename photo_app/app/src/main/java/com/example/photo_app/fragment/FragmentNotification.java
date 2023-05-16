package com.example.photo_app.fragment;

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
import com.example.photo_app.adapter.NotificationAdapter;
import com.example.photo_app.adapter.ratingComment.CommentListAdapter;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.ratingComment.CommentService;
import com.example.photo_app.model.notification.NotificationModel;
import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotification extends Fragment {
    private static final String TAG = "FragmentNotification";
//    EditText eComment;
    private ListView listView;
//    private ImageView mBackArrow, ivCheckMark;

    NotificationAdapter adapter;


    private List<NotificationModel> mNotifications;

    public FragmentNotification(List<NotificationModel> mNotifications) {
        this.mNotifications = mNotifications;
    }


//    private CommentService commentService;
//    private String photoId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        commentService = ApiClient.createService(CommentService.class, getContext());
        listView = (ListView) view.findViewById(R.id.listView);
//        mNotifications = new ArrayList<>();
//        adapter = new CommentListAdapter(this,
//                R.layout.layout_comment, mComments);
        adapter = new NotificationAdapter(getActivity(), mNotifications);
//        adapter.setPhotoId(photoId);
        listView.setAdapter(adapter);

//        getAllComments();

    }

    //call api
//    public void getAllComments() {
//        Log.d(TAG, "getAllComments: getting a list of all comments");
//        commentService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Comment>>>() {
//
//            @Override
//            public void onResponse(Call<MessageResponse<List<Comment>>> call, Response<MessageResponse<List<Comment>>> response) {
//                if(response.isSuccessful()){
//                    Log.d(TAG, "onResponse: get all comment success");
//                    List<Comment> comments = response.body().getData();
//                    Long clientId = response.body().getClient();
//                    for(Comment comment : comments){
//                        Log.d(TAG, "onResponse: comment: " + comment.getMessage());
//                    }
//                    adapter.setUserId(clientId);
//                    adapter.updateComments(comments);
//                    listView.invalidateViews();
//                    adapter.notifyDataSetChanged();
//                }else{
//                    Log.d(TAG, "onResponse: get all comment fail");
//                    Log.d(TAG, response.code() + " " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MessageResponse<List<Comment>>> call, Throwable t) {
//                Log.d(TAG, "onFailure: get all comment fail");
//            }
//        });
//    }

}
