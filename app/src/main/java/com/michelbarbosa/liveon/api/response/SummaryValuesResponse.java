package com.michelbarbosa.liveon.api.response;

import com.google.gson.annotations.SerializedName;

public class SummaryValuesResponse {

	@SerializedName("monthly_price")
	private String monthlyPrice;

	@SerializedName("total_price")
	private String totalPrice;

	@SerializedName("extras")
	private String extras;

	public String getMonthlyPrice(){
		return monthlyPrice;
	}

	public String getTotalPrice(){
		return totalPrice;
	}

	public String getExtras(){
		return extras;
	}
}