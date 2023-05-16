package com.example.photo_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button registry_button, back_button;
    private EditText username, password, address, fullname;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registry_button = findViewById(R.id.btnRegistry);
        backArrow = findViewById(R.id.backArrow);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        fullname = findViewById(R.id.fullname);
        final Context context = getApplicationContext(); // Lấy Context của ứng dụng
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", MODE_PRIVATE);
                String token = sharedPreferences.getString("accessToken", "");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Loading..."); // thiết lập tin nhắn
                progressDialog.show(); // hiển thị ProgressDialog
                User user = new User(null,null, username.getText().toString(), password.getText().toString(),
                        fullname.getText().toString(), address.getText().toString(), null);

                Context context = getApplicationContext(); // Lấy Context của ứng dụng

                UserService userService = ApiClient.createService(UserService.class, context);
                Call<Message> call = userService.checkRegister(user);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful()) {
                            Message message = response.body();
                            Toast.makeText(RegisterActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            progressDialog.dismiss();
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Error: Username is already taken!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Unable to call server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

