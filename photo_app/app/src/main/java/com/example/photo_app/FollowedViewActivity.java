package com.example.photo_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photo_app.adapter.RecycleViewAdapterUser;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;

import java.util.List;

import retrofit2.Call;

public class FollowedViewActivity extends ProfileViewActivity {
    private RecyclerView recycleView;
    private RecycleViewAdapterUser adapter;

    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_followed);
        recycleView = findViewById(R.id.recycleView);
        backArrow = findViewById(R.id.backArrow);

        // lấy thông tin user từ intent
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        adapter = new RecycleViewAdapterUser();
        Context context = getApplicationContext();
        UserService userService = ApiClient.createService(UserService.class, context);
        Call<List<User>> call = userService.getFollowed(user.getId());
        call.enqueue(new retrofit2.Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users == null)
                        Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Found " + users.size() + " users", Toast.LENGTH_SHORT).show();
                        for (User user : users) {
                            Log.d("TAG", "onResponse: " + user.toString());
                        }
                        adapter.setList(users);
                        recycleView.setAdapter(adapter);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recycleView.setLayoutManager(manager);
                        recycleView.setAdapter(adapter);
                        adapter.setItemListener(new RecycleViewAdapterUser.ItemListener() {
                            @Override
                            public void OnItemClick(View view, int p) {
                                User user = adapter.getItem(p);
                                Intent intent = new Intent(getApplicationContext(), ProfileViewActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
