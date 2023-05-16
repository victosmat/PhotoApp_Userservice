package com.example.photo_app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.photo_app.R;
import com.example.photo_app.adapter.PostAdapter;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.api.PostApiClient;
import com.example.photo_app.api.PostService;
import com.example.photo_app.model.Post;
import com.example.photo_app.model.PostImgs;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // thiết lập tin nhắn
        progressDialog.show(); // hiển thị ProgressDialog
        // call get api to retrieve following ids using retrofit
        PostApiClient postApiClient = new PostApiClient(getContext());
        postApiClient.getFeed(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Post> posts = response.body();
                    // Initialize RecyclerView and its adapter
                    RecyclerView recyclerView = view.findViewById(R.id.recycleViewItemPost);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    FlickrService flickrService = GoClient.createServiceNonCookie(FlickrService.class, getActivity());
                    PostAdapter postAdapter = new PostAdapter(posts, postApiClient, getActivity(), flickrService);
                    recyclerView.setAdapter(postAdapter);
                    progressDialog.dismiss();
                    // notify adapter that data has changed
                    postAdapter.notifyDataSetChanged();

                } else {
                    // handle request errors depending on status code
                    Log.e("Error 1: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                // handle failure
                Log.e("Error: ", t.getMessage());
            }
        });

    }
}
