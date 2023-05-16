package com.example.photo_app.fragment.ratingComment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.ImageActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.ratingComment.RatingListAdapter;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.ratingComment.RatingService;
import com.example.photo_app.model.ratingComment.Rating;
import com.example.photo_app.model.ratingComment.RatingDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewRatings extends Fragment {
    private static final String TAG = "ViewRatingsFragment";
    private ListView listView;
    private Context mContext;
    private ImageView mBackArrow;
    private Button btnRate;
    private boolean rated;

    RatingListAdapter adapter;

    private List<Rating> mRatings;

    private RatingService ratingService;
    private String photoId;
    private Long userId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_ratings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ratingService = ApiClient.createService(RatingService.class, getContext());
        photoId = getArguments().getString("photo_id");
        userId = getArguments().getLong("user_id");
//        Rating = view.findViewById(R.id.comment);
//        ivCheckMark = (ImageView) view.findViewById(R.id.ivPostRating);
        btnRate = view.findViewById(R.id.btnRate);
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        listView = (ListView) view.findViewById(R.id.listView);
        mRatings = new ArrayList<>();
//        adapter = new RatingListAdapter(this,
//                R.layout.layout_comment, mRatings);
        adapter = new RatingListAdapter(getActivity(), mRatings,btnRate);
        adapter.setPhotoId(photoId);
        listView.setAdapter(adapter);
        //Add new rating
        btnRate.setOnClickListener(v -> {
            LinearLayout linearLayout = new LinearLayout(v.getContext());
            EditText editView = new EditText(v.getContext());
            RatingBar ratingBar = new RatingBar(v.getContext());
            ratingBar.setNumStars(5);
            ratingBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(editView);
            linearLayout.addView(ratingBar);
            AlertDialog.Builder editDialog = new AlertDialog.Builder(v.getContext());
            editDialog.setTitle("CREATE RATING");
            editDialog.setView(linearLayout);
            editDialog.setPositiveButton("Rate", (dialog, which) -> {
                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setMessage(editView.getText().toString());
                ratingDTO.setRating((int)ratingBar.getRating());
                ratingDTO.setPhotoId(photoId);
                ratingService.create(ratingDTO,userId.toString()).enqueue(new Callback<MessageResponse<Rating>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Rating>> call, Response<MessageResponse<Rating>> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: success delete");
//                            notifyDataSetChanged();
                            Log.d(TAG, "getAllRatings: getting a list of all ratings");
                            getAllRatings();
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
            editDialog.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog dialog = editDialog.create();
            dialog.show();
        });

        getAllRatings();

        mBackArrow.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating back");
            getActivity().getSupportFragmentManager().popBackStack();
            ImageActivity context = (ImageActivity) getContext();
            context.showLayout();

        });
    }

    //call api
    public void getAllRatings() {
        Log.d(TAG, "getAllRatings: getting a list of all ratings");
        ratingService.getAllByPhoto(photoId).enqueue(new Callback<MessageResponse<List<Rating>>>() {

            @Override
            public void onResponse(Call<MessageResponse<List<Rating>>> call, Response<MessageResponse<List<Rating>>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: get all ratings success");
                    List<Rating> ratings = response.body().getData();
                    ratings.sort((c1,c2) -> (int) (c2.getId() - c1.getId()));
                    Long clientId = response.body().getClient();
                    adapter.setUserId(clientId);
                    adapter.updateRatings(ratings);
                    listView.invalidateViews();
                    adapter.notifyDataSetChanged();
                    for(Rating rating : ratings){
                        if(rating.getUserId()==clientId){
                            Log.d(TAG, "onResponse: ratings: " + rating.getUserId());
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
                Log.d(TAG, "onFailure: get all ratings fail");
            }
        });
    }
}
