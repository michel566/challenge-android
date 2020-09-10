package com.michelbarbosa.liveon.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderVehicle implements Parcelable {

    private int orderId;
    private String submodel_name;

    public OrderVehicle(int orderId, String submodel_name) {
        this.orderId = orderId;
        this.submodel_name = submodel_name;
    }

    protected OrderVehicle(Parcel in) {
        orderId = in.readInt();
        submodel_name = in.readString();
    }

    public static final Creator<OrderVehicle> CREATOR = new Creator<OrderVehicle>() {
        @Override
        public OrderVehicle createFromParcel(Parcel in) {
            return new OrderVehicle(in);
        }

        @Override
        public OrderVehicle[] newArray(int size) {
            return new OrderVehicle[size];
        }
    };

    public int getOrderId() {
        return orderId;
    }

    public String getSubmodel_name() {
        return submodel_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(submodel_name);
    }
}
