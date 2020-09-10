package com.michelbarbosa.liveon.data.orders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.api.response.OrderResponse;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.domain.OrderDetailsVehicle;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.mapper.LiveOnMappers;

import java.util.ArrayList;
import java.util.List;

public class VehicleViewModel extends AndroidViewModel {

    private VehicleRepository repository;
    private LiveData<List<OrderVehicleEntity>> orderVEntity;
    private MutableLiveData<List<OrderVehicleEntity>> orderVEntityResult;
    private MutableLiveData<List<OrderDetailsVehicle>> vehicleResult;
    private LiveData<List<VehicleEntity>> vehicleList;
    private LiveData<List<ImageVehicleEntity>> imageList;

    public VehicleViewModel(Application application) {
        super(application);
        repository = new VehicleRepository(application);
        orderVEntity = repository.getmOrderVList();
        orderVEntityResult = repository.getmOrderVListResult();
        vehicleResult = repository.getmOrderDetailsListResult();
        vehicleList = repository.getmVehicleList();
        imageList = repository.getmImageVehicleList();
    }

    public void insertOrderVehicle(List<OrderResponse> responseList) {
        List<OrderVehicleEntity> orderEntityList = new ArrayList<>();
        int i = 0;
        for (OrderResponse order : responseList) {
            orderEntityList.add(
                    new OrderVehicleEntity(order.getOrder_id(), order.getSubmodel_name()));
        }
        repository.insertOrderVEntityList(orderEntityList);
    }

    public LiveData<List<OrderVehicleEntity>> getOrderVEntity() {
        return orderVEntity;
    }

    public MutableLiveData<List<OrderVehicleEntity>> getOrderVEntityResult() {
        return orderVEntityResult;
    }

    public void insertVehicleDetail(OrderDetailsResponse response, int orderId) {
        VehicleEntity vehicleEntity = LiveOnMappers.vehicleMapperToEntity(response, orderId);
        List<ImageVehicleEntity> listImages = LiveOnMappers.imageVehicleListToEntity(response, vehicleEntity.getId());
        repository.insertVehicleDetail(new OrderDetailsVehicle(vehicleEntity, listImages));
    }

    public MutableLiveData<List<OrderDetailsVehicle>> getVehicleResult() {
        return vehicleResult;
    }

    public LiveData<List<VehicleEntity>> getVehicleList() {
        return vehicleList;
    }

    public LiveData<List<ImageVehicleEntity>> getImageList() {
        return imageList;
    }
}
