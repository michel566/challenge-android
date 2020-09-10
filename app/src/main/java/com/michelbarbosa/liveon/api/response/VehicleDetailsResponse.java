package com.michelbarbosa.liveon.api.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VehicleDetailsResponse {

	@SerializedName("vehicle_brand")
	private String vehicleBrand;

	@SerializedName("engine_type")
	private String engineType;

	@SerializedName("km")
	private int km;

	@SerializedName("engine")
	private String engine;

	@SerializedName("image_url")
	private List<String> imageUrl;

	@SerializedName("vehicle_year")
	private int vehicleYear;

	@SerializedName("vehicle_model")
	private String vehicleModel;

	@SerializedName("delivery_delay")
	private int deliveryDelay;

	@SerializedName("fuel_type")
	private String fuelType;

	@SerializedName("doors_qtd")
	private int doorsQtd;

	public String getVehicleBrand(){
		return vehicleBrand;
	}

	public String getEngineType(){
		return engineType;
	}

	public int getKm(){
		return km;
	}

	public String getEngine(){
		return engine;
	}

	public List<String> getImageUrl(){
		return imageUrl;
	}

	public int getVehicleYear(){
		return vehicleYear;
	}

	public String getVehicleModel(){
		return vehicleModel;
	}

	public int getDeliveryDelay(){
		return deliveryDelay;
	}

	public String getFuelType(){
		return fuelType;
	}

	public int getDoorsQtd(){
		return doorsQtd;
	}
}