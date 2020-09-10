package com.michelbarbosa.liveon.domain;

import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;

import java.util.List;

public class OrderDetailsVehicle {

    private VehicleEntity entity;
    private List<ImageVehicleEntity> images;

    public OrderDetailsVehicle(VehicleEntity entity, List<ImageVehicleEntity> images) {
        this.entity = entity;
        this.images = images;
    }

    public VehicleEntity getEntity() {
        return entity;
    }

    public List<ImageVehicleEntity> getImages() {
        return images;
    }

    public void setEntity(VehicleEntity entity) {
        this.entity = entity;
    }

    public void setImages(List<ImageVehicleEntity> images) {
        this.images = images;
    }
}