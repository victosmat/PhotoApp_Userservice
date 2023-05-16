package com.example.photo_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;
import com.example.photo_app.model.call.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewActivity extends AppCompatActivity {

    private ImageView backArrow;
    private TextView tvFollowers, tvFollowing, tvName, tvAddress;
    private LinearLayout lnListFollowers, lnListFollowing;
    private User user;

    private Button btnFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_profile);
        backArrow = findViewById(R.id.backArrow);

        tvFollowers = findViewById(R.id.tvFollowers);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        lnListFollowers = findViewById(R.id.lnListFollowers);
        lnListFollowing = findViewById(R.id.lnListFollowing);
        btnFollow = findViewById(R.id.btnFollow);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Context context = getApplicationContext();
        UserService userService = ApiClient.createService(UserService.class, context);
        Call<Message> CheckFollow = userService.checkFollow(user.getId());
        CheckFollow.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message message = response.body();
                    if (message.getMessage().equals("Success")) {
                        btnFollow.setText("Unfollow");
                    } else {
                        btnFollow.setText("Follow");
                    }
                } else {
                    Log.d("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        lnListFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, FollowedViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        lnListFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, FollowingViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


        Call<List<User>> call = userService.getFollowing(user.getId());
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

        call = userService.getFollowed(user.getId());
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

        Call<User> callGetUserById = userService.getUserById(user.getId());
        callGetUserById.enqueue(new Callback<User>() {
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


        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFollow.getText().equals("Follow")) {
                    Call<Message> callFollow = userService.follow(user.getId());
                    callFollow.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                btnFollow.setText("Unfollow");
                            } else {
                                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
                    builder.setTitle("User unfollow notification");
                    builder.setMessage("Are you sure you want to unfollow " + user.getFullName() + " ?");
                    builder.setIcon(R.drawable.remove);
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        Call<Message> callUnfollow = userService.unfollow(user.getId());
                        callUnfollow.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Unfollowed", Toast.LENGTH_SHORT).show();
                                    btnFollow.setText("Follow");
                                } else {
                                    Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}
