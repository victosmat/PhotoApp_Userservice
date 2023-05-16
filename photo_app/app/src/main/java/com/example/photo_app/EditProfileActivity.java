package com.example.photo_app;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;
import com.example.photo_app.model.call.Message;

import java.util.List;

import retrofit2.Call;

public class EditProfileActivity extends AppCompatActivity {

    public EditProfileActivity() {
    }

    private Button btnSubmit, btnBack;
    private EditText fullname, address, username, email, authProvider;
    private ImageView backArrow;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editprofile);
        btnSubmit = findViewById(R.id.btnSubmit);
//        btnBack = findViewById(R.id.btnBack);
        backArrow = findViewById(R.id.backArrow);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        authProvider = findViewById(R.id.authProvider);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Username cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        authProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Auth Provider cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        Context context = getApplicationContext();

        UserService userService = ApiClient.createService(UserService.class, context);
        Call<User> callGetUserFromJWT = userService.getUserFromJWT();
        callGetUserFromJWT.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                user = response.body();
                fullname.setText(user.getFullName());
                address.setText(user.getAddress());
                username.setText(user.getUsername());
                email.setText(user.getEmail());
                authProvider.setText(user.getAuthProvider());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                progressDialog.setMessage("Loading..."); // thiết lập tin nhắn
                progressDialog.show(); // hiển thị ProgressDialog
                String fullNameStr = fullname.getText().toString();
                String addressStr = address.getText().toString();
                String usernameStr = username.getText().toString();

                User userCall = new User(user.getId(), user.getEmail(), usernameStr, user.getPassword(), fullNameStr, addressStr, user.getAuthProvider());

                Call<Message> callUpdateUser = userService.updateUser(userCall);
                callUpdateUser.enqueue(new retrofit2.Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {
                        Message message = response.body();
                        Toast.makeText(EditProfileActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        progressDialog.dismiss();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
