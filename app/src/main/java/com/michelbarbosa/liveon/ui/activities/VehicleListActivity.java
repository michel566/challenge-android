package com.michelbarbosa.liveon.ui.activities;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michelbarbosa.liveon.R;
import com.michelbarbosa.liveon.api.request.LiveOnRequest;
import com.michelbarbosa.liveon.api.request.LiveOnRequestContracts;
import com.michelbarbosa.liveon.api.response.OrderResponse;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.domain.OrderVehicle;
import com.michelbarbosa.liveon.mapper.LiveOnMappers;
import com.michelbarbosa.liveon.ui.adapters.VehicleListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.michelbarbosa.liveon.ui.ConstantesUi.TOKEN_PREF;

public class VehicleListActivity extends MainActivity
        implements LiveOnRequestContracts.OrderListView {

    private List<OrderVehicle> mOrderVehicleList;

    private VehicleListAdapter adapter;
    private VehicleListAdapter.ItemClickListener itemClickListener;
    private LiveOnRequestContracts.Presenter orderPresenter = new LiveOnRequest(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutContent(R.layout.activity_vehicle_list);
        toolbarSettings();
        setOperationList();
        setViews();
        setObservers();
        orderPresenter.loadOrdersResponse(this, sharedPreferences.getString(TOKEN_PREF, ""));
    }

    private void toolbarSettings() {
        setLightToolbarColor();
        setToolbarTitle(R.string.assinaturas);
        setToolbarArrowBackPressed();
    }

    private void setViews() {
        RecyclerView rvVehicleList = findViewById(R.id.rv_vehicle_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new VehicleListAdapter(itemClickListener);
        rvVehicleList.setLayoutManager(layoutManager);
        rvVehicleList.setAdapter(adapter);
    }

    @Override
    public void orderListLoadSuccess(List<OrderResponse> responseList) {
        vehicleViewModel.insertOrderVehicle(responseList);
        setObservers();
    }

    @Override
    public void orderListLoadFailed(String error) {

    }

    private void setObservers() {
        vehicleViewModel.getOrderVEntity().observe(this, new Observer<List<OrderVehicleEntity>>() {
            @Override
            public void onChanged(List<OrderVehicleEntity> orderVEntityList) {
                if (!orderVEntityList.isEmpty()) {
                    mOrderVehicleList = new ArrayList<>(LiveOnMappers.orderEntityToOrderVehicleEntity(orderVEntityList));
                    adapter.setOrderList(mOrderVehicleList);
                }
            }
        });

        vehicleViewModel.getOrderVEntityResult().observe(this, new Observer<List<OrderVehicleEntity>>() {
            @Override
            public void onChanged(List<OrderVehicleEntity> orderVEntityList) {
                if (!orderVEntityList.isEmpty()) {
                    mOrderVehicleList = new ArrayList<>(LiveOnMappers.orderEntityToOrderVehicleEntity(orderVEntityList));
                    adapter.setOrderList(mOrderVehicleList);
                }
            }
        });
    }

    private void setOperationList() {
        itemClickListener = new VehicleListAdapter.ItemClickListener() {
            @Override
            public void onClick(OrderVehicle orderVehicle) {
                advanceToVehicleActivity(VehicleListActivity.this, orderVehicle);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToUserProfileActivity(this);
    }
}
