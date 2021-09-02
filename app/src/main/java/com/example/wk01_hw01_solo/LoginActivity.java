package com.example.wk01_hw01_solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//testing for login branch
public class LoginActivity extends AppCompatActivity {
    private String TAG = "LOGIN ACTIVITY";
    EditText loginUser, loginPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginUser = findViewById(R.id.loginUser);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<List<User>> call = jsonPlaceHolderApi.getUser();

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (!response.isSuccessful()){
                            Log.d(TAG, "Network Error");
                            return;
                        }

                        List<User> users = response.body();

                        for(User user: users){
                            Log.d(TAG, loginUser.getText().toString() + " user.getUsername()" + loginPassword.getText().toString() + " user.getPassword()\n");
                            if(loginUser.getText().toString().equals(user.getUsername()) && loginPassword.getText().toString().equals(user.getPassword())){
                                openMainActivity(user.getUserId(), user.getUsername(), user.getName());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            }
        });
    }

    public void openMainActivity(int userId, String username, String name){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("userId", userId);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}