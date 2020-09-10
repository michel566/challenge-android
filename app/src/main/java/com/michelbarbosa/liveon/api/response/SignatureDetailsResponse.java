package com.michelbarbosa.liveon.api.response;

import com.google.gson.annotations.SerializedName;

public class SignatureDetailsResponse {

	@SerializedName("plan_type")
	private int planType;

	@SerializedName("months")
	private int months;

	@SerializedName("additional_franchise")
	private String additionalFranchise;

	public int getPlanType(){
		return planType;
	}

	public int getMonths(){
		return months;
	}

	public String getAdditionalFranchise(){
		return additionalFranchise;
	}
}