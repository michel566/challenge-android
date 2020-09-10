package com.michelbarbosa.liveon.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = OrderVehicleEntity.class, parentColumns = "pk_order_id",
        childColumns = "fk_order_id", onDelete = ForeignKey.CASCADE))
public class VehicleEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pk_vehicle_id")
    private int id;

    @ColumnInfo(name = "vehicle_brand")
    private String vehicleBrand;

    @ColumnInfo(name ="engine_type")
    private String engineType;

    @ColumnInfo(name ="km")
    private int km;

    @ColumnInfo(name ="engine")
    private String engine;

    @ColumnInfo(name ="vehicle_year")
    private int vehicleYear;

    @ColumnInfo(name ="vehicle_model")
    private String vehicleModel;

    @ColumnInfo(name ="delivery_delay")
    private int deliveryDelay;

    @ColumnInfo(name ="fuel_type")
    private String fuelType;

    @ColumnInfo(name ="doors_qtd")
    private int doorsQtd;

    @ColumnInfo(name ="plan_type")
    private int planType;

    @ColumnInfo(name ="months")
    private int months;

    @ColumnInfo(name ="additional_franchise")
    private String additionalFranchise;

    @ColumnInfo(name ="monthly_price")
    private String monthlyPrice;

    @ColumnInfo(name ="total_price")
    private String totalPrice;

    @ColumnInfo(name ="extras")
    private String extras;

    @NonNull
    @ColumnInfo(name = "fk_order_id", index = true)
    private int orderId;

    public VehicleEntity(int id, String vehicleBrand, String engineType, int km, String engine, int vehicleYear,
                         String vehicleModel, int deliveryDelay, String fuelType, int doorsQtd, int planType,
                         int months, String additionalFranchise, String monthlyPrice,
                         String totalPrice, String extras, int orderId) {
        this.id = id;
        this.vehicleBrand = vehicleBrand;
        this.engineType = engineType;
        this.km = km;
        this.engine = engine;
        this.vehicleYear = vehicleYear;
        this.vehicleModel = vehicleModel;
        this.deliveryDelay = deliveryDelay;
        this.fuelType = fuelType;
        this.doorsQtd = doorsQtd;
        this.planType = planType;
        this.months = months;
        this.additionalFranchise = additionalFranchise;
        this.monthlyPrice = monthlyPrice;
        this.totalPrice = totalPrice;
        this.extras = extras;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public String getEngineType() {
        return engineType;
    }

    public int getKm() {
        return km;
    }

    public String getEngine() {
        return engine;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public int getDeliveryDelay() {
        return deliveryDelay;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getDoorsQtd() {
        return doorsQtd;
    }

    public int getPlanType() {
        return planType;
    }

    public int getMonths() {
        return months;
    }

    public String getAdditionalFranchise() {
        return additionalFranchise;
    }

    public String getMonthlyPrice() {
        return monthlyPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getExtras() {
        return extras;
    }

    public int getOrderId() {
        return orderId;
    }
}
