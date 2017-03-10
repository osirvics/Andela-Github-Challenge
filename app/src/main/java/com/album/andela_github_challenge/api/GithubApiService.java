package com.album.andela_github_challenge.api;


import com.album.andela_github_challenge.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * User Search api.
 * */
public interface GithubApiService {
    @GET("search/users?q=location:lagos+language:java")
    Call<User> getSearchUsers(@Query("page") int page,
                              @Query("per_page") int per_page);
}
