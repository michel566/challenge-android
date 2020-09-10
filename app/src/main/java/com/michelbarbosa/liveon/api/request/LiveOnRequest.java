package com.michelbarbosa.liveon.api.request;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.michelbarbosa.liveon.api.DataService;
import com.michelbarbosa.liveon.api.network.RetrofitClientInstance;
import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.api.response.OrderResponse;
import com.michelbarbosa.liveon.api.response.UserProfileResponse;
import com.michelbarbosa.liveon.mapper.LiveOnMappers;
import com.michelbarbosa.liveon.utils.UiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveOnRequest implements LiveOnRequestContracts.Presenter {

    private LiveOnRequestContracts.AuthView authView;
    private LiveOnRequestContracts.UserProfileView userProfileView;
    private LiveOnRequestContracts.OrderListView orderListView;
    private LiveOnRequestContracts.VehicleDetailsView vehicleDetailsView;

    public LiveOnRequest(LiveOnRequestContracts.AuthView authView) {
        this.authView = authView;
    }

    public LiveOnRequest(LiveOnRequestContracts.UserProfileView userProfileView) {
        this.userProfileView = userProfileView;
    }

    public LiveOnRequest(LiveOnRequestContracts.OrderListView orderListView) {
        this.orderListView = orderListView;
    }

    public LiveOnRequest(LiveOnRequestContracts.VehicleDetailsView vehicleDetailsView) {
        this.vehicleDetailsView = vehicleDetailsView;
    }

    @Override
    public void login(Context context, String email, String password) {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<UserProfileResponse> call = service.Auth(email, password);
        serviceAuthSignUp(context, call);
    }

    @Override
    public void loadUserProfileResponse(Context context, String token) {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<UserProfileResponse> call = service.Profile(token);
        serviceLoadUserProfile(context, call);
    }

    @Override
    public void loadOrdersResponse(Context context, String token) {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<List<OrderResponse>> call = service.Orders(token);
        serviceLoadOrderResponse(context, call);
    }

    @Override
    public void loadVehicleDetailResponse(Context context, String token, String orderId) {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<OrderDetailsResponse> call = service.OrderDetails(token, orderId);
        serviceLoadVehicleDetailResponse(context, call);
    }

    private void serviceAuthSignUp(final Context context, Call<UserProfileResponse> call) {
        final AlertDialog requestDialog = UiUtil.progressDialog(context, "Autenticando...");
        requestDialog.show();
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                requestDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        authView.signUpCallbackSuccess(LiveOnMappers.getToken(response.body()));
                    } else {
                        authView.signUpCallbackFailed("Erro " + response.code() + "->" + response.toString());
                    }
                } else {
                    try {
                        //TODO: TRATAMENTO DE ERRO E DA RESPOSTA DO ERRORBODY
                        authView.signUpCallbackFailed("Erro " + response.errorBody());
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.e(context.getClass().getName(), t.getMessage());
                }
                authView.signUpCallbackFailed(t.getMessage());
                requestDialog.dismiss();
            }
        });
    }

    private void serviceLoadUserProfile(final Context context, Call<UserProfileResponse> call) {
        final AlertDialog requestDialog = UiUtil.progressDialog(context, "Carregando...");
        requestDialog.show();
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                requestDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        userProfileView.userProfileLoadSuccess(response.body());
                    } else {
                        userProfileView.userProfileloadFailed("corpo do html nulo");
                    }
                } else {
                    userProfileView.userProfileloadFailed("não há resposta enviado do servidor");
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                userProfileView.userProfileloadFailed("Falha na tentativa de conexão");
                requestDialog.dismiss();
            }
        });
    }

    private void serviceLoadOrderResponse(Context context, Call<List<OrderResponse>> call) {
        final AlertDialog requestDialog = UiUtil.progressDialog(context, "Carregando...");
        requestDialog.show();
        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                requestDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        orderListView.orderListLoadSuccess(response.body());
                    } else {
                        orderListView.orderListLoadFailed("corpo do html nulo");
                    }
                } else {
                    orderListView.orderListLoadFailed("não há resposta enviado do servidor");
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                orderListView.orderListLoadFailed("Falha na tentativa de conexão");
                requestDialog.dismiss();
            }
        });
    }

    private void serviceLoadVehicleDetailResponse(Context context, Call<OrderDetailsResponse> call) {
        final AlertDialog requestDialog = UiUtil.progressDialog(context, "Carregando...");
        requestDialog.show();
        call.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                requestDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        vehicleDetailsView.vehicleLoadSuccess(response.body());
                    } else {
                        vehicleDetailsView.vehicleLoadFailed("corpo do html nulo");
                    }
                } else {
                    vehicleDetailsView.vehicleLoadFailed("não há resposta enviado do servidor");
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                vehicleDetailsView.vehicleLoadFailed("Falha na tentativa de conexã");
                requestDialog.dismiss();
            }
        });
    }

}
