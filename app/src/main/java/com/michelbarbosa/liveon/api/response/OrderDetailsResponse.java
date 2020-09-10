package com.michelbarbosa.liveon.api.response;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsResponse {

	@SerializedName("summary_values")
	private SummaryValuesResponse summaryValuesResponse;

	@SerializedName("signature_details")
	private SignatureDetailsResponse signatureDetailsResponse;

	@SerializedName("vehicle_details")
	private VehicleDetailsResponse vehicleDetailsResponse;

	public SummaryValuesResponse getSummaryValuesResponse(){
		return summaryValuesResponse;
	}

	public SignatureDetailsResponse getSignatureDetailsResponse(){
		return signatureDetailsResponse;
	}

	public VehicleDetailsResponse getVehicleDetailsResponse(){
		return vehicleDetailsResponse;
	}
}