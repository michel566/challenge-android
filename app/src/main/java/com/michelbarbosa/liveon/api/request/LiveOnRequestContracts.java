package com.michelbarbosa.liveon.api.request;

import android.content.Context;

import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.api.response.OrderResponse;
import com.michelbarbosa.liveon.api.response.UserProfileResponse;

import java.util.List;

public interface LiveOnRequestContracts {

    interface AuthView {
        void signUpCallbackSuccess(String token);

        void signUpCallbackFailed(String error);

        void signInCallbackSuccess();

        void signInCallbackFailed();
    }

    interface UserProfileView {
        void userProfileLoadSuccess(UserProfileResponse response);

        void userProfileloadFailed(String error);
    }

    interface OrderListView {
        void OrderListLoadSuccess(List<OrderResponse> responseList);

        void OrderListLoadFailed(String error);
    }

    interface VehicleDetailsView {
        void vehicleLoadSuccess(OrderDetailsResponse vehicleResponse);

        void vehicleLoadFailed(String error);
    }

    interface Presenter {
        void login(Context context, String email, String password);

        void login(Context context, String token);

        void loadUserProfileResponse(Context context, String token);

        void loadOrdersResponse(Context context, String token);

        void loadVehicleDetailResponse(Context context, String token, String orderId);

    }


}
