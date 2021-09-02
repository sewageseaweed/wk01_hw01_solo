package com.example.wk01_hw01_solo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPost();

    @GET("users")
    Call<List<User>> getUser();
}
