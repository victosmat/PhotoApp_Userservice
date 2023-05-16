package com.example.photo_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photo_app.AlbumActivity;
import com.example.photo_app.EditProfileActivity;
import com.example.photo_app.FollowedViewActivity;
import com.example.photo_app.FollowingViewActivity;
import com.example.photo_app.LoginActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.ImagePagerAdapter;
import com.example.photo_app.adapter.RecycleViewAdapterImage;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;
import com.example.photo_app.model.call.flickr.PhotosByUserResponse;
import com.example.photo_app.model.call.flickr.PhotosetsResponse;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment implements RecycleViewAdapterImage.ItemListener {

    private User user;
    private RecycleViewAdapterImage recycleViewAdapterImage;
    private Button btnEditProfile;
    private Button btnAlbums;
    private TextView tvFollowers, tvFollowing, tvName, tvAddress;
    private LinearLayout lnListFollowers, lnListFollowing;
    private RecyclerView recyclerView;
    private final List<PhotosByUserResponse.photoByUserResponse>[] list = new List[]{new ArrayList<>()};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        lnListFollowers = view.findViewById(R.id.lnListFollowers);
        lnListFollowing = view.findViewById(R.id.lnListFollowing);
        btnAlbums = view.findViewById(R.id.btnAlbums);
        recyclerView = view.findViewById(R.id.recycleView);

        recycleViewAdapterImage = new RecycleViewAdapterImage(getActivity(), this);
        Context context = getContext();

        UserService userService = ApiClient.createService(UserService.class, context);
        Call<List<User>> listCall = userService.getUsersByFollowing();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> following = response.body();
                    Log.d("TAG", "onResponse following: " + following.size());
                    if (following != null) tvFollowing.setText(String.valueOf(following.size()));
                    else tvFollowing.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        listCall = userService.getUsersByFollowed();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followers = response.body();
                    Log.d("TAG", "onResponse followers: " + followers.size());
                    if (followers != null) tvFollowers.setText(String.valueOf(followers.size()));
                    else tvFollowers.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        Call<User> callGetUserFromJWT = userService.getUserFromJWT();
        callGetUserFromJWT.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user != null) {
                    tvName.setText(user.getFullName());
                    tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        lnListFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FollowedViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        lnListFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FollowingViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        // kiểm tra xem sharepreference flirck bằng true hay false
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("flickr", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("flickr", false);

        if (isLogin == false) {
            Toast.makeText(getActivity(), "Not login with flickr yet", Toast.LENGTH_SHORT).show();
        } else {
            FlickrService flickrService = GoClient.createServiceNonCookie(FlickrService.class, getActivity());
            Call<PhotosByUserResponse> call = flickrService.getImageByUserId("id");
            call.enqueue(new Callback<PhotosByUserResponse>() {
                @Override
                public void onResponse(Call<PhotosByUserResponse> call, Response<PhotosByUserResponse> response) {
                    System.out.println("Success get photo by response");
                    list[0] = response.body().getPhotos();
                    recycleViewAdapterImage.setList(list[0]);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(recycleViewAdapterImage);

                    recycleViewAdapterImage.setItemListener(new RecycleViewAdapterImage.ItemListener() {
                        @Override
                        public void OnItemClick(View view, int p) {
//                String id = list.get(p).getId();
////                Intent intent = new Intent(getActivity(), ImageActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<PhotosByUserResponse> call, Throwable t) {
                    System.out.println("Failed get photo by response");
                }
            });
//        PhotosByUserResponse urlImage = new PhotosByUserResponse();

//       urlImage.setPhotos(list);
        }


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        btnAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieManager cookieManager = FragmentUpload.getCookieManager();
                FlickrService flickrService = GoClient.createService(FlickrService.class, getActivity(), cookieManager);
                Call<PhotosetsResponse> photosetsResponseCall = flickrService.getPhotosetByUserId();
                photosetsResponseCall.enqueue(new Callback<PhotosetsResponse>() {
                    @Override
                    public void onResponse(Call<PhotosetsResponse> call, Response<PhotosetsResponse> response) {
                       if(response.isSuccessful()){
                           System.out.println("SUCCESS with size of " + response.body().getResponse().size());
                           ArrayList<PhotosetsResponse.PhotosetResponse> photosetsResponseList = (ArrayList<PhotosetsResponse.PhotosetResponse>) response.body().getResponse();

                           startActivity(new Intent(getActivity(), AlbumActivity.class).putExtra("photosets_response", photosetsResponseList));
                       }
                    }

                    @Override
                    public void onFailure(Call<PhotosetsResponse> call, Throwable t) {
                        System.out.println("FAILED");
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recycleViewAdapterImage = new RecycleViewAdapterImage(getActivity(), this);
        // kiểm tra xem sharepreference flirck bằng true hay false
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("flickr", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("flickr", false);
        String userID = sharedPreferences.getString("user_id", "");

        if (isLogin == false) {
            Toast.makeText(getActivity(), "Not login with flickr yet", Toast.LENGTH_SHORT).show();
        } else {
            FlickrService flickrService = GoClient.createServiceNonCookie(FlickrService.class, getActivity());
            Call<PhotosByUserResponse> call = flickrService.getImageByUserId(userID);
            call.enqueue(new Callback<PhotosByUserResponse>() {
                @Override
                public void onResponse(Call<PhotosByUserResponse> call, Response<PhotosByUserResponse> response) {
                    System.out.println("Success get photo by response");
                    list[0] = response.body().getPhotos();

                    recycleViewAdapterImage.setList(list[0]);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(recycleViewAdapterImage);

                    recycleViewAdapterImage.setItemListener(new RecycleViewAdapterImage.ItemListener() {
                        @Override
                        public void OnItemClick(View view, int p) {
//                String id = list.get(p).getId();
////                Intent intent = new Intent(getActivity(), ImageActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<PhotosByUserResponse> call, Throwable t) {
                    System.out.println("Failed get photo by response");
                }
            });
//        PhotosByUserResponse urlImage = new PhotosByUserResponse();

//       urlImage.setPhotos(list);
        }
        Context context = getContext();

        UserService userService = ApiClient.createService(UserService.class, context);
        Call<List<User>> call = userService.getUsersByFollowing();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> following = response.body();
                    Log.d("TAG", "onResponse following: " + following.size());
                    if (following != null) tvFollowing.setText(String.valueOf(following.size()));
                    else tvFollowing.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        call = userService.getUsersByFollowed();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followers = response.body();
                    Log.d("TAG", "onResponse followers: " + followers.size());
                    if (followers != null) tvFollowers.setText(String.valueOf(followers.size()));
                    else tvFollowers.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        Call<User> callGetUserFromJWT = userService.getUserFromJWT();
        callGetUserFromJWT.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user != null) {
                    tvName.setText(user.getFullName());
                    tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int p) {

    }
}
