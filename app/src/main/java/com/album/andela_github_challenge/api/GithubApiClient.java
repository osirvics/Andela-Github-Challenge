package com.album.andela_github_challenge.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GithubApiClient {
    public static final String GITHUB_API_BASE_URL = "https://api.github.com/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GITHUB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
