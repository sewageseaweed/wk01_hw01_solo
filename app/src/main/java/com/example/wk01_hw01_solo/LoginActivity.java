package com.example.wk01_hw01_solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

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
                        String password =  loginPassword.getText().toString();
                        String username = loginUser.getText().toString();

                        Log.d(TAG, loginUser.getText().toString() + " user.getUsername()" + loginPassword.getText().toString() + " user.getPassword()\n");
                        if(!checkUsername(users, username) && !checkPassword(password)){
                            Context context = getApplicationContext();
                            CharSequence text = "Incorrect Username and Password! Please try again!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            loginUser.setBackgroundColor(0x55FF0000);
                            loginPassword.setBackgroundColor(0x55FF0000);
                        }
                        else if (!checkUsername(users, username)){
                            Context context = getApplicationContext();
                            CharSequence text = "Incorrect Username!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            loginPassword.setBackgroundColor(0x00000000);
                            loginUser.setBackgroundColor(0x55FF0000);
                        }
                        else if (!checkPassword(password)){
                            Context context = getApplicationContext();
                            CharSequence text = "Incorrect Password!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            loginUser.setBackgroundColor(0x00000000);
                            loginPassword.setBackgroundColor(0x55FF0000);
                        }
                        else{
                            loginUser.setBackgroundColor(0x00000000);
                            loginPassword.setBackgroundColor(0x00000000);
                            for(User user: users) {
                                if (username.equals(user.getUsername())) {
                                    openMainActivity(user.getUserId(), username, user.getName());
                                }
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

    public static boolean checkUsername(List<User> users, String username){
        for(User user: users){
            if(username.equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

    public static boolean checkPassword(String password){
        return(password.equals("Password123"));
    }
}