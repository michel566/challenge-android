package com.michelbarbosa.liveon.data.orders;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;

import java.util.List;

@Dao
public interface VehicleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderVehicleEntity(List<OrderVehicleEntity> orderVehicleEntityList);

    @Query("SELECT * FROM OrderVehicleEntity")
    List<OrderVehicleEntity> findOrderVehicleEntityList();

    @Query("SELECT * FROM OrderVehicleEntity")
    LiveData<List<OrderVehicleEntity>> getOrderVehicleEntityList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImageListEntity(List<ImageVehicleEntity> vehicle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVehicleEntity(VehicleEntity vehicle);

    @Query("SELECT * FROM ImageVehicleEntity")
    List<ImageVehicleEntity> findImageVehicleList();

    @Query("SELECT * FROM VehicleEntity")
    List<VehicleEntity> findVehicleEntityList();

    @Query("SELECT * FROM ImageVehicleEntity")
    LiveData<List<ImageVehicleEntity>> getImageVehicleList();

    @Query("SELECT * FROM VehicleEntity")
    LiveData<List<VehicleEntity>> getVehicleEntityList();

    @Query("DELETE FROM OrderVehicleEntity")
    void deleteOrderVehicle();

    @Query("DELETE FROM VehicleEntity")
    void deleteVehicleEntity();

    @Query("DELETE FROM ImageVehicleEntity")
    void deleteImageVehicleEntity();

}
