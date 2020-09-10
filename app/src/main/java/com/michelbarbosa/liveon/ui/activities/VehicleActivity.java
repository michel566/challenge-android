package com.michelbarbosa.liveon.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.michelbarbosa.liveon.R;
import com.michelbarbosa.liveon.api.request.LiveOnRequest;
import com.michelbarbosa.liveon.api.request.LiveOnRequestContracts;
import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.data.entities.OrderEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.domain.OrderDetailsVehicle;
import com.michelbarbosa.liveon.domain.OrderVehicle;
import com.michelbarbosa.liveon.mapper.LiveOnMappers;
import com.michelbarbosa.liveon.ui.adapters.VehicleImageAdapter;
import com.michelbarbosa.liveon.utils.UiUtil;

import java.util.List;

import static com.michelbarbosa.liveon.ui.ConstantesUi.ORDER_VEHICLE;
import static com.michelbarbosa.liveon.ui.ConstantesUi.TOKEN_PREF;

public class VehicleActivity extends MainActivity implements LiveOnRequestContracts.VehicleDetailsView {

    private LiveOnRequestContracts.Presenter vehiclePresenter = new LiveOnRequest(this);

    private OrderVehicle mOrder;
    private OrderDetailsVehicle mVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutContent(R.layout.activity_vehicle);
        mOrder = getIntent().getParcelableExtra(ORDER_VEHICLE);
        toolbarSettings();

        setObservers();
        vehiclePresenter.loadVehicleDetailResponse(this,
                sharedPreferences.getString(TOKEN_PREF, ""),
                String.valueOf(mOrder.getOrderId()));
    }

    private void toolbarSettings() {
        setDefaultToolbarColor();
        getOrder();
        setToolbarArrowBackPressed();
    }

    private void getOrder() {
        userViewModel.getOrderList().observe(this, new Observer<List<OrderEntity>>() {
            @Override
            public void onChanged(List<OrderEntity> orderEntityList) {
                for (OrderEntity order : orderEntityList) {
                    if (order.getOrderId() == mOrder.getOrderId()) {
                        setToolbarTitle(order.getSubmodel_name());
                    }
                }
            }
        });
    }

    private void setObservers() {
        vehicleViewModel.getImageList().observe(this, new Observer<List<ImageVehicleEntity>>() {
            @Override
            public void onChanged(List<ImageVehicleEntity> imageVehicleEntities) {
                if (!imageVehicleEntities.isEmpty()) {
                    setImageToAdapter(imageVehicleEntities);
                }
            }
        });

        vehicleViewModel.getVehicleList().observe(VehicleActivity.this, new Observer<List<VehicleEntity>>() {
            @Override
            public void onChanged(List<VehicleEntity> vehicleEntities) {
                if (!vehicleEntities.isEmpty()) {
                    for (VehicleEntity vehicle : vehicleEntities) {
                        if (vehicle.getOrderId() == mOrder.getOrderId()) {
                            setViews(vehicle);
                        }
                    }
                }
            }
        });

        vehicleViewModel.getVehicleResult().observe(this, new Observer<List<OrderDetailsVehicle>>() {
            @Override
            public void onChanged(List<OrderDetailsVehicle> vehicleList) {
                if (!vehicleList.isEmpty()) {
                    mVehicle = LiveOnMappers.getOrderDetailsVehicle(vehicleList, mOrder.getOrderId());
                }
            }
        });
    }

    @Override
    public void vehicleLoadSuccess(OrderDetailsResponse response) {
        vehicleViewModel.insertVehicleDetail(response, mOrder.getOrderId());
    }

    @Override
    public void vehicleLoadFailed(String error) {
    }

    private void setViews(VehicleEntity vehicle) {
        if (vehicle != null) {

        }

        TextView tvFuelType = findViewById(R.id.tv_fuelType_vehicle);
        TextView tvQtdDoor = findViewById(R.id.tv_doorQtd_vehicle);

        //teste de esconder a toolbar
        tvFuelType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.GONE);
            }
        });

        tvQtdDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
            }
        });


    }

    private void setImageToAdapter(List<ImageVehicleEntity> imageList) {
        if (imageList != null) {
            ViewPager viewPgVehicleList = findViewById(R.id.viewPgCarouselVehicle);
            VehicleImageAdapter vehicleImageAdapter = new VehicleImageAdapter(this, imageList);
            viewPgVehicleList.setAdapter(vehicleImageAdapter);

            LinearLayout sliderDotsPanel = findViewById(R.id.ll_vehicle_SliderDots);
            UiUtil.dotPagerGenerator(this, viewPgVehicleList, sliderDotsPanel,
                    vehicleImageAdapter.getCount());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToVehicleListActivity(this);
    }


        /*
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

 */

}
