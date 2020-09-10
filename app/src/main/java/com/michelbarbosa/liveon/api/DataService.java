package com.michelbarbosa.liveon.api;

import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.api.response.OrderResponse;
import com.michelbarbosa.liveon.api.response.UserProfileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @POST("auth")
    Call<UserProfileResponse> Auth(@Query("email") String email,
                                   @Query("password") String password);

    @GET("user/profile?")
    Call<UserProfileResponse> Profile(@Query("token") String token);

    @GET("user/profile/orders?")
    Call<List<OrderResponse>> Orders(@Query("token") String token);

    @GET("user/profile/order_details?")
    Call<OrderDetailsResponse> OrderDetails(
            @Query("token") String token,
            @Query("order_id") String order_id);

}
