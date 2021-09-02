package com.example.wk01_hw01_solo;

import com.google.gson.annotations.SerializedName;

public class User {

    private String username;
    private String password = "Password123";
    private String name;

    @SerializedName("id")
    private int userId;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }
}
