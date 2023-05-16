package com.example.photo_app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photo_app.ProfileViewActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.PostAdapter;
import com.example.photo_app.adapter.RecycleViewAdapterUser;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.api.PostApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.Post;
import com.example.photo_app.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment implements RecycleViewAdapterUser.ItemListener {

    private RecycleViewAdapterUser adapter;
    private EditText etSearch;
    private RadioGroup radioGroup;
    private Button btnSearch;
    private RecyclerView recycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        recycleView = view.findViewById(R.id.recycleView);
        radioGroup = view.findViewById(R.id.radio_group);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading..."); // thiết lập tin nhắn
                progressDialog.show(); // hiển thị ProgressDialog
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    int scopeID = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = radioGroup.findViewById(scopeID);
                    String object = radioButton.getText().toString();
                    String keyword = etSearch.getText().toString();
                    Log.d("TAG", "onClick: " + object + " " + keyword);
                    if (keyword.isEmpty()) keyword = " ";
                    if (object.equals("User")) {
                        adapter = new RecycleViewAdapterUser();
                        Context context = getContext();
                        UserService userService = ApiClient.createService(UserService.class, context);
                        Call<List<User>> call = userService.getUsersByKeyword(keyword);
                        call.enqueue(new retrofit2.Callback<List<User>>() {
                            @Override
                            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    List<User> users = response.body();
                                    if (users == null)
                                        Toast.makeText(getContext(), "No user found", Toast.LENGTH_SHORT).show();
                                    else {
                                        Toast.makeText(getContext(), "Found " + users.size() + " users", Toast.LENGTH_SHORT).show();
                                        for (User user : users) {
                                            Log.d("TAG", "onResponse: " + user.toString());
                                        }
                                        adapter.setList(users);
                                        recycleView.setAdapter(adapter);
                                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                        recycleView.setLayoutManager(manager);
                                        recycleView.setAdapter(adapter);
                                        adapter.setItemListener(new RecycleViewAdapterUser.ItemListener() {
                                            @Override
                                            public void OnItemClick(View view, int p) {
                                                User user = adapter.getItem(p);
                                                Intent intent = new Intent(getActivity(), ProfileViewActivity.class);
                                                intent.putExtra("user", user);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<User>> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.d("TAG", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                    else if (object.equals("Post")) {
                        PostApiClient postApiClient = new PostApiClient(getContext());
                        postApiClient.getPostsByKeyword(keyword, new Callback<ArrayList<Post>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    ArrayList<Post> posts = response.body();
                                    // Initialize RecyclerView and its adapter
                                    recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    FlickrService flickrService = GoClient.createServiceNonCookie(FlickrService.class, getActivity());
                                    PostAdapter postAdapter = new PostAdapter(posts, postApiClient, getActivity(), flickrService);
                                    recycleView.setAdapter(postAdapter);
                                    // notify adapter that data has changed
                                    postAdapter.notifyDataSetChanged();

                                } else {
                                    progressDialog.dismiss();
                                    // handle request errors depending on status code
                                    Log.e("Error 1: ", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                                progressDialog.dismiss();
                                // handle failure
                                Log.e("Errorwtf: ", t.getMessage());
                            }
                        });
                    }
                } else{
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Please select a object", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void OnItemClick(View view, int p) {
        User user = adapter.getItem(p);
        Intent intent = new Intent(getActivity(), ProfileViewActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
