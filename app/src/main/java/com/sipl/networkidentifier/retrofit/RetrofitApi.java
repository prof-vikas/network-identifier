package com.sipl.networkidentifier.retrofit;

import com.sipl.networkidentifier.dto.response.UserResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitApi {

    @GET("users/{id}")
    Call<UserResponseDto> getUserById(@Path("id") String id);

}
