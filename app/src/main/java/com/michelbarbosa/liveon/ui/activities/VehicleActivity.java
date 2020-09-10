package com.michelbarbosa.liveon.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.michelbarbosa.liveon.R;
import com.michelbarbosa.liveon.api.request.LiveOnRequest;
import com.michelbarbosa.liveon.api.request.LiveOnRequestContracts;
import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.domain.OrderVehicle;
import com.michelbarbosa.liveon.ui.adapters.VehicleImageAdapter;
import com.michelbarbosa.liveon.utils.UiUtil;

import java.util.List;

import static com.michelbarbosa.liveon.ui.ConstantesUi.ORDER_VEHICLE;
import static com.michelbarbosa.liveon.ui.ConstantesUi.TOKEN_PREF;

public class VehicleActivity extends MainActivity implements LiveOnRequestContracts.VehicleDetailsView {

    private LiveOnRequestContracts.Presenter vehiclePresenter = new LiveOnRequest(this);

    private OrderVehicle mOrder;

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
        setToolbarArrowBackPressed();
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
    }

    @Override
    public void vehicleLoadSuccess(OrderDetailsResponse response) {
        vehicleViewModel.insertVehicleDetail(response, mOrder.getOrderId());
        setObservers();
    }

    @Override
    public void vehicleLoadFailed(String error) {
    }

    private void setViews(VehicleEntity vehicle) {
        if (vehicle != null) {
            setToolbarTitle(vehicle.getVehicleBrand() +
                    " " + vehicle.getVehicleModel());
            TextView tvYearBrand = findViewById(R.id.tv_yearBrand_vehicle);
            TextView tvModel = findViewById(R.id.tv_model_vehicle);
            TextView tvMonthValue = findViewById(R.id.tv_monthValue_vehicle);
            TextView tvFuelType = findViewById(R.id.tv_fuelType_vehicle);
            TextView tvQtdDoor = findViewById(R.id.tv_doorQtd_vehicle);
            TextView tvEngineType = findViewById(R.id.tv_engineType_vehicle);
            TextView tvEngine = findViewById(R.id.tv_engine_vehicle);
            ImageView ivDeliveryIsOk = findViewById(R.id.iv_deliveryDelay_vehicle);
            TextView tvDeliveryDelay = findViewById(R.id.tv_deliveryDelay_vehicle);
            TextView tvKm = findViewById(R.id.tv_km_vehicle);
            TextView tvMonthSignature = findViewById(R.id.tv_monthSignature_vehicle);
            TextView tvPlan = findViewById(R.id.tv_planType_vehicle);
            TextView tvFranchAdd = findViewById(R.id.tv_addFranch_vehicle);
            TextView tvMonthP = findViewById(R.id.tv_monthP_vehicle);
            TextView tvExtras = findViewById(R.id.tv_extras_vehicle);
            TextView tvTotal = findViewById(R.id.tv_total_vehicle);

            tvYearBrand.setText(new StringBuilder(
                    vehicle.getVehicleYear()).append(" ")
                    .append(vehicle.getVehicleBrand()));
            tvModel.setText(vehicle.getVehicleModel());
            tvMonthValue.setText(getResources().getString(R.string.tv_currency,
                    String.valueOf(vehicle.getMonthlyPrice())));
            tvFuelType.setText(vehicle.getFuelType());
            tvQtdDoor.setText(getResources().getString(R.string.tv_doorQtd_vehicle,
                    String.valueOf(vehicle.getDoorsQtd())));
            tvEngineType.setText(vehicle.getEngineType());
            tvEngine.setText(getResources().getString(R.string.tv_engine_vehicle,
                    vehicle.getEngine()));
            if(vehicle.getDeliveryDelay() > 0){
                tvDeliveryDelay.setText(
                        getResources().getString(R.string.tv_deliveryDelay_vehicle,
                                String.valueOf(vehicle.getDeliveryDelay())));
                ivDeliveryIsOk.setVisibility(View.VISIBLE);
            } else {
                ivDeliveryIsOk.setVisibility(View.GONE);
            }
            tvKm.setText(getResources().getString(R.string.tv_km_vehicle,
                    String.valueOf(vehicle.getKm())));
            tvMonthSignature.setText(String.valueOf(vehicle.getMonths()));
            tvPlan.setText(String.valueOf(vehicle.getPlanType()));
            tvFranchAdd.setText(getResources().getString(R.string.tv_currency,
                    vehicle.getAdditionalFranchise().replace(".", ",")));
            tvMonthP.setText(getResources().getString(R.string.tv_currency,
                    vehicle.getMonthlyPrice() + ",00"));
            tvExtras.setText(getResources().getString(R.string.tv_currency,
                    vehicle.getExtras() + ",00"));
            tvTotal.setText(getResources().getString(R.string.tv_currency,
                    vehicle.getTotalPrice() + ",00"));
        }
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

}
